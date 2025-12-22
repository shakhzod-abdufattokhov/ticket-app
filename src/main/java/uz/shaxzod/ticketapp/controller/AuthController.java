package uz.shaxzod.ticketapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.shaxzod.ticketapp.models.entity.User;
import uz.shaxzod.ticketapp.models.requestDto.security.RefreshTokenRequest;
import uz.shaxzod.ticketapp.models.requestDto.security.SignInRequest;
import uz.shaxzod.ticketapp.models.requestDto.security.SignUpRequest;
import uz.shaxzod.ticketapp.models.responseDto.RefreshTokenResponse;
import uz.shaxzod.ticketapp.models.responseDto.security.AuthResponse;
import uz.shaxzod.ticketapp.service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@Valid @RequestBody SignUpRequest request,
                                               HttpServletRequest httpRequest) {
        AuthResponse response = authService.signUp(request, httpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@Valid @RequestBody SignInRequest request,
                                               HttpServletRequest httpRequest
    ) {
        AuthResponse response = authService.signIn(request, httpRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request,
                                                     HttpServletRequest httpRequest
    ) {
        RefreshTokenResponse response = authService.refreshToken(request, httpRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signout")
    public ResponseEntity<Map<String, String>> signOut(@AuthenticationPrincipal User user) {
        authService.signOut(user.getId());
        return ResponseEntity.ok(Map.of("message", "Signed out successfully"));
    }
}