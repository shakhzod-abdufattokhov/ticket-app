package uz.shaxzod.ticketapp.models.responseDto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VenueResponse {
    private String name;
    private String address;
    private String city;
    private String phoneNumber;
    private List<EventPreview> events;
}
