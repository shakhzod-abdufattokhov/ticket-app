package uz.shaxzod.ticketapp.models.responseDto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EventPreview {
    private String title;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String lowestPrice;
}
