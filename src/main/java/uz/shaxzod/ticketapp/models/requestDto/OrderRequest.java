package uz.shaxzod.ticketapp.models.requestDto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderRequest {
    @NotNull(message = "Reservation id can not be null")
    private List<String> reservationId;
}
