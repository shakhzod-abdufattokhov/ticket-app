package uz.shaxzod.ticketapp.models.requestDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VenueRequest {
    @NotNull(message = "Name can't be null")
    private String name;
    @NotNull(message = "City can't be null")
    private String city;
    @NotNull(message = "Address can't be null")
    private String address;
    @NotNull(message = "Phone number can't be null")
    @Pattern(regexp = "^9{2}8[0-9]{2}[0-9]{7}$", message = "phone number must be valid format")
    private String phoneNumber;
}
