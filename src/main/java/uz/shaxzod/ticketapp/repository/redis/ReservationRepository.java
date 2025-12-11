package uz.shaxzod.ticketapp.repository.redis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uz.shaxzod.ticketapp.models.redis.Reservation;

import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, String> {
    List<Reservation> findByShowIdAndSeatId(String showId, String seatId);
}
