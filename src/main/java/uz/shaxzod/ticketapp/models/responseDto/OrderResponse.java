package uz.shaxzod.ticketapp.models.responseDto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderResponse {
    private String id;
    private String userFirstName;
    private String userSecondName;
    private String venueName;
    private String showTime;
    private String showDay;
    private List<Integer> seatNumber;
    private List<String> seatLabel;
}
