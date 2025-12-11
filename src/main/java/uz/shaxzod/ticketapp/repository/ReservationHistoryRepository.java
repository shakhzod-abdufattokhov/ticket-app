package uz.shaxzod.ticketapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.shaxzod.ticketapp.models.entity.ReservationHistory;

import java.util.List;

public interface ReservationHistoryRepository  extends JpaRepository<ReservationHistory, String> {
    List<ReservationHistory> findByShowIdAndSeatId(String showId, String seatId);
}
