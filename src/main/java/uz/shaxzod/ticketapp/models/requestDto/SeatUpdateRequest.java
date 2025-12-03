package uz.shaxzod.ticketapp.models.requestDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SeatUpdateRequest {
    private String type;
    private String seatLabel;
}
