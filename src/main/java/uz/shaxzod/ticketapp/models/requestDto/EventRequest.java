package uz.shaxzod.ticketapp.models.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventRequest {
    private Long venueId;
    private String title;
    private String description;
    private String type;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String language;
    private Integer minAge;
}
