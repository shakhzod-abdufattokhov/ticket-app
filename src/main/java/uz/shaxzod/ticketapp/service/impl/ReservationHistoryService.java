package uz.shaxzod.ticketapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.shaxzod.ticketapp.exceptions.CustomNotFoundException;
import uz.shaxzod.ticketapp.models.entity.ReservationHistory;
import uz.shaxzod.ticketapp.models.entity.Seat;
import uz.shaxzod.ticketapp.models.entity.Show;
import uz.shaxzod.ticketapp.models.enums.ReservStatus;
import uz.shaxzod.ticketapp.repository.ReservationHistoryRepository;
import uz.shaxzod.ticketapp.repository.SeatRepository;
import uz.shaxzod.ticketapp.repository.ShowRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationHistoryService {
    private final ReservationHistoryRepository historyRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;

    public String create(String userId, String showId, String seatId) {
        log.info("Creating ReservationHistory request");
        Show show = showRepository.findById(showId).orElseThrow(
                () -> new CustomNotFoundException("Show not found with id: " + showId));

        Seat seat = seatRepository.findById(seatId).orElseThrow(
                () -> new CustomNotFoundException("Seat not found with id: " + seatId));

        ReservationHistory history = ReservationHistory.builder()
                .userId(userId == null ? "" : userId)
                .showId(showId)
                .seatId(seatId)
                .eventName(show.getEvent().getTitle())
                .seatLabel(seat.getSeatLabel())
                .currentStatus(ReservStatus.HELD)
                .build();
        history = historyRepository.save(history);
        return history.getId();
    }

    public String update(String showId, String seatId, ReservStatus status) {
        log.info("Update ReservationHistory request");
        List<ReservationHistory> reservationHistory = historyRepository.findByShowIdAndSeatId(showId, seatId);
        log.info("{}", reservationHistory);
        if (reservationHistory.isEmpty()) {
            throw new CustomNotFoundException("Reservation history not found");
        }

        ReservationHistory history = reservationHistory.get(0);
        history.setOldStatus(history.getCurrentStatus());
        history.setCurrentStatus(status);

        history = historyRepository.save(history);
        return history.getId();
    }

}
