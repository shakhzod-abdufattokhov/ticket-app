package uz.shaxzod.ticketapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.shaxzod.ticketapp.exceptions.CustomNotFoundException;
import uz.shaxzod.ticketapp.mapper.ReservationMapper;
import uz.shaxzod.ticketapp.models.enums.ReservStatus;
import uz.shaxzod.ticketapp.models.redis.Reservation;
import uz.shaxzod.ticketapp.models.requestDto.ReservationRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.repository.redis.ReservationRepository;
import uz.shaxzod.ticketapp.service.ReservationService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper mapper;
    private final ReservationHistoryService historyService;

    @Override
    public ApiResponse<String> create(ReservationRequest request) {
        log.info("Creating Reservation request: " + request);
        List<Reservation> reservations = reservationRepository.findByShowIdAndSeatId(request.getShowId(), request.getSeatId());
        if (!reservations.isEmpty()) {
            return ApiResponse.error("Seat is already reserved");
        }
        Reservation reservation = mapper.toEntity(request);
        reservation = reservationRepository.save(reservation);
        historyService.create(request.getUserId(), request.getShowId(), request.getSeatId(), reservation.getId());
        return ApiResponse.success(reservation.getId(), "Seat is reserved");
    }

    @Override
    public ApiResponse<Long> get(String id) {
        log.info("Get Reservation request with id: {}", id);
        Reservation reservation = reservationRepository.findById(id).orElseThrow(
                () -> new CustomNotFoundException("Reservation not found with id: "+ id));
        return ApiResponse.success(reservation.getLifeTimeInMin());
    }


    @Override
    public ApiResponse<Void> cancel(String id) {
        log.info("Cancel reservation request with id: {}", id);
        Reservation reservation = reservationRepository.findById(id).orElseThrow(
                () -> new CustomNotFoundException("Reservation not found with id: "+ id));
        reservationRepository.deleteById(id);
        historyService.updateHistory(reservation.getId(), ReservStatus.CANCELLED);
        return ApiResponse.success("Canceled successfully");
    }

    @Override
    public void handleExpiration(String reservationId) {
        log.info("Handling Reservation when expired request by redisReservationId: {}", reservationId);
        historyService.updateHistory(reservationId, ReservStatus.EXPIRED);
        ApiResponse.success("Expired");
    }

}
