package uz.shaxzod.ticketapp.models.requestDto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SectorRequest {
    @NotNull(message = "Venue id can not be null")
    private String venueId;
    @NotNull(message = "Sector name can not be null")
    private String name;
}
