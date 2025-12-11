package uz.shaxzod.ticketapp.models.requestDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReservationRequest {
    private String userId;
    private String showId;
    private String seatId;
}
