package uz.shaxzod.ticketapp.models.responseDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CategoryResponse {
    private String id;
    private String type;
    private Long price;
}
