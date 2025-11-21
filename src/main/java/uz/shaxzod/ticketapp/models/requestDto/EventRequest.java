package uz.shaxzod.ticketapp.models.requestDto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Venue can't be null")
    private Long venueId;
    @NotNull(message = "Title can't be null")
    private String title;
    private String description;
    @NotNull(message = "Type can not be null")
    private String type;
    private String language;
    private Short minAge;
}
