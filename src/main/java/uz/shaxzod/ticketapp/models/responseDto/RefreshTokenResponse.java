package uz.shaxzod.ticketapp.models.responseDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RefreshTokenResponse {
    private String token;
    private String refreshToken;
}
