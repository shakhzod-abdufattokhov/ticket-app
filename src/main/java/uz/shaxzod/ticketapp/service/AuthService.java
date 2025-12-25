package uz.shaxzod.ticketapp.service;

import io.jsonwebtoken.security.Password;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.shaxzod.ticketapp.config.JwtProperties;
import uz.shaxzod.ticketapp.exceptions.CustomAlreadyExistException;
import uz.shaxzod.ticketapp.exceptions.CustomLockedException;
import uz.shaxzod.ticketapp.exceptions.CustomNotFoundException;
import uz.shaxzod.ticketapp.models.entity.PasswordResetToken;
import uz.shaxzod.ticketapp.models.entity.RefreshToken;
import uz.shaxzod.ticketapp.models.entity.User;
import uz.shaxzod.ticketapp.models.enums.Role;
import uz.shaxzod.ticketapp.models.requestDto.ForgotPasswordRequest;
import uz.shaxzod.ticketapp.models.requestDto.ResetPasswordRequest;
import uz.shaxzod.ticketapp.models.requestDto.security.RefreshTokenRequest;
import uz.shaxzod.ticketapp.models.requestDto.security.SignInRequest;
import uz.shaxzod.ticketapp.models.requestDto.security.SignUpRequest;
import uz.shaxzod.ticketapp.models.responseDto.RefreshTokenResponse;
import uz.shaxzod.ticketapp.models.responseDto.security.AuthResponse;
import uz.shaxzod.ticketapp.models.responseDto.security.UserInfo;
import uz.shaxzod.ticketapp.repository.PasswordResetTokenRepository;
import uz.shaxzod.ticketapp.repository.UserRepository;
import uz.shaxzod.ticketapp.service.email.EmailService;
import uz.shaxzod.ticketapp.service.security.JwtService;
import uz.shaxzod.ticketapp.utility.SecurityUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtProperties jwtProperties;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;

    @Value("${ticket.base-url}")
    private String baseUrl;


    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final long LOCK_TIME_DURATION = 15; // minutes

    @Transactional
    public AuthResponse signUp(SignUpRequest request, HttpServletRequest httpRequest) {
        log.info("Sign up attempt for phone: {}", request.getPhoneNumber());

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new CustomAlreadyExistException("Phone number already registered");
        }

        if(userRepository.existsByEmail(request.getEmail())){
            throw new CustomAlreadyExistException("Email is already registered");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(Role.USER);
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);

        User savedUser = userRepository.save(user);
        log.info("User registered successfully: {}", savedUser.getId());

        return generateAuthResponse(savedUser, httpRequest);
    }

    @Transactional(noRollbackFor = BadCredentialsException.class)
    public AuthResponse signIn(SignInRequest request, HttpServletRequest httpRequest) {
        log.info("Sign in attempt for phone: {}", request.getPhoneNumber());

        User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if (!user.getAccountNonLocked()) {
            if (user.getLockTime() != null && ChronoUnit.MINUTES.between(user.getLockTime(), LocalDateTime.now()) > LOCK_TIME_DURATION) {
                user.setAccountNonLocked(true);
                user.setFailedLoginAttempts(0);
                user.setLockTime(null);
                userRepository.save(user);
            } else {
                throw new CustomLockedException("Account is locked due to multiple failed login attempts. Try again later.");
            }
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getPhoneNumber(), request.getPassword()));

            if (user.getFailedLoginAttempts() > 0) {
                user.setFailedLoginAttempts(0);
                user.setLockTime(null);
                userRepository.save(user);
            }

            log.info("User signed in successfully: {}", user.getId());
            return generateAuthResponse(user, httpRequest);

        } catch (BadCredentialsException e) {
            handleFailedLogin(user);
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    @Transactional
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request, HttpServletRequest httpRequest) {
        log.info("Token refresh attempt");

        RefreshToken refreshToken = refreshTokenService.findByToken(request.getRefreshToken());
        refreshToken = refreshTokenService.verifyExpiration(refreshToken);

        User user = refreshToken.getUser();
        String accessToken = jwtService.generateAccessToken(user);

        log.info("Token refreshed successfully for user: {}", user.getId());
        return RefreshTokenResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    @Transactional
    public void signOut(String userId) {
        log.info("Sign out for user: {}", userId);
        refreshTokenService.revokeAllUserTokens(userId);
    }

    public UserInfo getCurrentUser(){
        Optional<User> currentUser = SecurityUtils.getCurrentUser();
        if(currentUser.isEmpty()){
            return null;
        }
        User user = currentUser.get();
        return UserInfo.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Transactional
    public void forgotPassword(ForgotPasswordRequest request){
        log.info("Forgot password request with email: {}", request);
        User user = userRepository.findByEmail(request.email()).orElseThrow(
                () -> new CustomNotFoundException("User not found with email: "+request.email()));

        tokenRepository.markAllTokensAsUsed(user.getId());

        String rawToken = UUID.randomUUID().toString();
        log.info("Token: {}", rawToken);
        String hashedToken = Sha512DigestUtils.shaHex(rawToken);
        log.info("Hashed token {}", hashedToken);

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .user(user)
                .tokenHash(hashedToken)
                .used(false)
                .expireAt(LocalDateTime.now().plusMinutes(15))
                .build();

        tokenRepository.save(resetToken);
        String resetLink = baseUrl + "/api/v1/auth/reset-password?token="+rawToken;
        emailService.sendResetPasswordMail(user.getEmail(), resetLink);
    }

    public void resetPassword(ResetPasswordRequest request){
        log.info("Reset password request: {}", request);
        String tokenHash = Sha512DigestUtils.shaHex(request.token());

        PasswordResetToken token = tokenRepository.findByTokenHash(tokenHash).orElseThrow(
                () -> new CustomNotFoundException("Invalid token"));

        if (token.getExpireAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        User user = token.getUser();

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);

        token.setUsed(true);
        tokenRepository.save(token);
    }

    private AuthResponse generateAuthResponse(User user, HttpServletRequest request) {
        String accessToken = jwtService.generateAccessToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user, request);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .expiresIn(jwtProperties.getAccessTokenExpiration())
                .user(mapToUserInfo(user))
                .build();
    }

    private void handleFailedLogin(User user) {
        Integer attempts = user.getFailedLoginAttempts() + 1;
        log.info("Num of Attempts user failed for login: {}", attempts);
        user.setFailedLoginAttempts(attempts);

        if (attempts >= MAX_FAILED_ATTEMPTS) {
            user.setAccountNonLocked(false);
            user.setLockTime(LocalDateTime.now());
            log.warn("Account locked due to {} failed login attempts: {}", attempts, user.getPhoneNumber());
        }
        User save = userRepository.save(user);
        log.info("Saved user: {}", save);
    }

    private UserInfo mapToUserInfo(User user) {
        return UserInfo.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}