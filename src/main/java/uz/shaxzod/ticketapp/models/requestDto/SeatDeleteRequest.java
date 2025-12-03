package uz.shaxzod.ticketapp.models.requestDto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SeatDeleteRequest {
    private List<String> seatIds;
}
