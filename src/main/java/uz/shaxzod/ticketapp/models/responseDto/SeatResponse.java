package uz.shaxzod.ticketapp.models.responseDto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatResponse {
    private String id;
    private String section;
    private Integer row;
    private Integer number;
    private String type;
    private String seatLabel;
}
