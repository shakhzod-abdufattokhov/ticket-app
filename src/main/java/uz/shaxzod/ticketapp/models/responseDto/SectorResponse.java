package uz.shaxzod.ticketapp.models.responseDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SectorResponse {
    String id;
    String name;
    String venueId;
}
