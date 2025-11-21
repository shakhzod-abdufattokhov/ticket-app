package uz.shaxzod.ticketapp.models.responseDto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EventDetailedResponse {
    private String title;
    private String description;
//    private LocalDate startDate; // the nearest day
    private List<ShowResponse> showResponseList;
    private Long lowestPrice;
    private Long venueId;
    private String status;
    private String type;
    private String language;
    private Short minAge;
}
