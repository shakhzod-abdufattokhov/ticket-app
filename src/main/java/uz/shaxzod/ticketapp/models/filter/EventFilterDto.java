package uz.shaxzod.ticketapp.models.filter;

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
public class EventFilterDto {
    private String search;
    private LocalDate date;
    private LocalTime time;
    private Long venueId;
    private Short age;
    private String language;
}
