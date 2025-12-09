package uz.shaxzod.ticketapp.models.requestDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoryRequest {
    private String venueId;
    private String type;
    private Long price;
}
