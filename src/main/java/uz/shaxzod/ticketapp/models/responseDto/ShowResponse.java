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
public class ShowResponse {
    private Long id;
    private LocalDate startDay;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<Long> seatIds;
}
