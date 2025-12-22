package uz.shaxzod.ticketapp.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.shaxzod.ticketapp.config.JwtProperties;
import uz.shaxzod.ticketapp.exceptions.TokenRefreshException;
import uz.shaxzod.ticketapp.models.entity.RefreshToken;
import uz.shaxzod.ticketapp.models.entity.User;
import uz.shaxzod.ticketapp.repository.RefreshTokenRepository;
import uz.shaxzod.ticketapp.service.security.JwtService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;
    private final JwtService jwtService;

    @Transactional
    public RefreshToken createRefreshToken(User user, HttpServletRequest request) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(jwtService.generateRefreshToken(user));
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(LocalDateTime.now().plusSeconds(jwtProperties.getRefreshTokenExpiration() / 1000));
        refreshToken.setDeviceInfo(extractDeviceInfo(request));
        refreshToken.setIpAddress(extractIpAddress(request));

        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional(readOnly = true)
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.isExpired()) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException("Refresh token was expired. Please sign in again.");
        }
        if (token.getRevoked() != null && token.getRevoked()) {
            throw new TokenRefreshException("Refresh token was revoked. Please sign in again.");
        }
        return token;
    }

    @Transactional(readOnly = true)
    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenRefreshException("Invalid refresh token"));
    }

    @Transactional
    public void revokeAllUserTokens(String userId) {
        refreshTokenRepository.revokeAllUserTokens(userId);
    }

    @Transactional
    @Scheduled(cron = "0 0 2 * * ?") // Run daily at 2 AM
    public void cleanupExpiredTokens() {
        log.info("Starting cleanup of expired refresh tokens");
        refreshTokenRepository.deleteExpiredTokens(LocalDateTime.now());
        log.info("Completed cleanup of expired refresh tokens");
    }

    private String extractDeviceInfo(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        return userAgent != null ? userAgent.substring(0, Math.min(userAgent.length(), 255)) : "Unknown";
    }

    private String extractIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}