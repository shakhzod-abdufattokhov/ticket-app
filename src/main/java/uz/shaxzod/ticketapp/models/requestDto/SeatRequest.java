package uz.shaxzod.ticketapp.models.requestDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
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
