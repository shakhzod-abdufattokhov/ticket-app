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
    private String row;
    private Integer number;
    private String type;
    private Long price;
    private String seatLabel;
}
