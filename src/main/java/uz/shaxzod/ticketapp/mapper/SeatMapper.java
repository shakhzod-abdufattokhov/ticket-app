package uz.shaxzod.ticketapp.mapper;

import org.springframework.stereotype.Component;
import uz.shaxzod.ticketapp.models.entity.Seat;
import uz.shaxzod.ticketapp.models.entity.SeatCategory;
import uz.shaxzod.ticketapp.models.entity.ShowSeats;
import uz.shaxzod.ticketapp.models.requestDto.SeatRequest;
import uz.shaxzod.ticketapp.models.requestDto.SeatUpdateRequest;
import uz.shaxzod.ticketapp.models.responseDto.SeatResponse;

import java.util.List;

@Component
public class SeatMapper {
    public Seat toEntity(SeatRequest request) {
        return Seat.builder()
                .row(request.getRow())
                .number(request.getNumber())
                .seatLabel(request.getSeatLabel())
                .build();

    }

    public SeatResponse toResponse(Seat seat){
        return SeatResponse.builder()
                .id(seat.getId())
                .number(seat.getNumber())
                .sectorId(seat.getSector().getId())
                .sectorName(seat.getSector().getName())
                .row(seat.getRow())
                .seatLabel(seat.getSeatLabel())
                .build();
    }

    public List<SeatResponse> toResponseList(List<Seat> seats) {
        return seats.stream()
                .map(this::toResponse)
                .toList();
    }

    public Seat toEntityUpdate(Seat seat, SeatUpdateRequest request) {
        seat.setSeatLabel(request.getSeatLabel());
        return seat;
    }

    public SeatResponse showSeatsToSeatResponse(ShowSeats showSeats){
        Seat seat = showSeats.getSeat();
        SeatCategory category = showSeats.getCategory();
        return SeatResponse.builder()
                .id(seat.getId())
                .number(seat.getNumber())
                .sectorId(seat.getSector().getId())
                .sectorName(seat.getSector().getName())
                .row(seat.getRow())
                .seatLabel(seat.getSeatLabel())
                .price(showSeats.getPrice() != null ? showSeats.getPrice() : category.getPrice())
                .build();
    }

    public List<SeatResponse> showSeatsToSeatResponseList(List<ShowSeats> showSeats) {
        return showSeats.stream()
                .map(this::showSeatsToSeatResponse)
                .toList();
    }
}
