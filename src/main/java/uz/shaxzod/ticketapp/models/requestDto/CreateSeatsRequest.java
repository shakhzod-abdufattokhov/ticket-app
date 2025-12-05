package uz.shaxzod.ticketapp.models.requestDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CreateSeatsRequest {
    private String venueId;
    private String sectorId;
    private Integer row;
    private Integer numOfSeats;
}
