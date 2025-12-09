package uz.shaxzod.ticketapp.models.responseDto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatResponse {
    private String id;
    private String sectorName;
    private String sectorId;
    private Integer row;
    private Integer number;
    private String seatLabel;
    private Long price;
}
