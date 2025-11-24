package uz.shaxzod.ticketapp.models.responseDto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ShowResponse {
    private String id;
    private LocalDate day;
    private LocalTime startTime;
    private LocalTime endTime;
//    private List<Long> seatIds;
}
