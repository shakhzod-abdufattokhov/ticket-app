package uz.shaxzod.ticketapp.mapper;

import org.springframework.stereotype.Component;
import uz.shaxzod.ticketapp.models.redis.Reservation;
import uz.shaxzod.ticketapp.models.requestDto.ReservationRequest;

@Component
public class ReservationMapper {

    public Reservation toEntity(ReservationRequest request) {
        return Reservation.builder()
                .seatId(request.getSeatId())
                .showId(request.getShowId())
                .lifeTimeInMin(1L)
                .build();
    }
}
