package uz.shaxzod.ticketapp.models.responseDto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class EventDetailedResponse {
    private String id;
    private String title;
    private String description;
//    private LocalDate startDate; // the nearest day
    private List<ShowResponse> showResponseList;
    private Long lowestPrice;
    private String venueId;
    private String status;
    private String type;
    private String language;
    private Short minAge;
}
