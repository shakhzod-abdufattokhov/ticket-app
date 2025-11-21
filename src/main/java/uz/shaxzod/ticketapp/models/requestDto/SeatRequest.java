package uz.shaxzod.ticketapp.models.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeatRequest {
    private Long venueId;
    private Long showId;
    private String section;
    private String row;
    private Integer number;
    private String type;
    private Long price;
    private String seatLabel;
}
