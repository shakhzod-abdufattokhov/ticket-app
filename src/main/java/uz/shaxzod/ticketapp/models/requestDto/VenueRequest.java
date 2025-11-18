package uz.shaxzod.ticketapp.models.requestDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VenueRequest {
    @NotNull(message = "Name can't be null")
    private String name;
    @NotNull(message = "City can't be null")
    private String city;
    @NotNull(message = "Address can't be null")
    private String address;
    @NotNull(message = "Phone number can't be null")
    private String phoneNumber;
}
