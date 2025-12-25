package uz.shaxzod.ticketapp.models.requestDto;

public record ResetPasswordRequest(
        String token,
        String newPassword,
        String confirmPassword
) {}
