package uz.shaxzod.ticketapp.models.responseDto;

import lombok.*;

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
    private String seatNumber;
    private String seatLabel;
}
