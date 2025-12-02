package uz.shaxzod.ticketapp.mapper;

import org.springframework.stereotype.Component;
import uz.shaxzod.ticketapp.models.entity.Seat;
import uz.shaxzod.ticketapp.models.enums.SeatType;
import uz.shaxzod.ticketapp.models.requestDto.SeatRequest;

@Component
public class SeatMapper {
    public Seat toEntity(SeatRequest request) {
        return Seat.builder()
                .section(request.getSection())
                .row(request.getRow())
                .number(request.getNumber())
                .type(SeatType.of(request.getType()))
                .seatLabel(request.getSeatLabel())
                .build();

    }
}
