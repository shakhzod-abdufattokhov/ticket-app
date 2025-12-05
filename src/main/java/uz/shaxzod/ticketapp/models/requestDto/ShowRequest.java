package uz.shaxzod.ticketapp.models.requestDto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ShowRequest {
    private String venueId;
    private String eventId;
    private LocalDate day;
    private Long basePrice;
    private LocalTime startTime;
    private LocalTime endTime;
}
