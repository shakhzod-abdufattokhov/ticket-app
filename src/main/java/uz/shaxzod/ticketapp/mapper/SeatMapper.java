package uz.shaxzod.ticketapp.mapper;

import org.springframework.stereotype.Component;
import uz.shaxzod.ticketapp.models.entity.Seat;
import uz.shaxzod.ticketapp.models.enums.SeatType;
import uz.shaxzod.ticketapp.models.requestDto.SeatRequest;
import uz.shaxzod.ticketapp.models.requestDto.SeatUpdateRequest;
import uz.shaxzod.ticketapp.models.responseDto.SeatResponse;

import java.util.List;

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

    public SeatResponse toResponse(Seat seat){
        return SeatResponse.builder()
                .id(seat.getId())
                .number(seat.getNumber())
                .section(seat.getSection())
                .row(seat.getRow())
                .type(seat.getType().toString())
                .seatLabel(seat.getSeatLabel())
                .build();
    }

    public List<SeatResponse> toResponseList(List<Seat> seats) {
        return seats.stream()
                .map(this::toResponse)
                .toList();
    }

    public Seat toEntityUpdate(Seat seat, SeatUpdateRequest request) {
        seat.setType(SeatType.of(request.getType()));
        seat.setSeatLabel(request.getSeatLabel());
        return seat;
    }
}
