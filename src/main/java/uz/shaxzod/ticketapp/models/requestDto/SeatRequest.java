package uz.shaxzod.ticketapp.models.requestDto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SeatRequest {
    @NotNull(message = "Venue id can not be null")
    private String venueId;
    private String sectorId;
    @NotNull(message = "Row can not be null")
    private Integer row;
    @NotNull(message = "Order number of seat can not be null")
    private Integer number;
    private String type;
    private String seatLabel;
}
