package uz.shaxzod.ticketapp.models.requestDto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ShowSeatsRequest {
    private List<String> seatIds;
    private String categoryId;
    private Long price;
}
