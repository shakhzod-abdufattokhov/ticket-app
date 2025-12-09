package uz.shaxzod.ticketapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.shaxzod.ticketapp.models.entity.ShowSeats;

import java.util.List;

@Repository
public interface ShowSeatsRepository extends JpaRepository<ShowSeats, String> {

    List<ShowSeats> findByShowId(String showId);

    List<ShowSeats> findByShowIdAndSeatIdIn(String id, List<String> seatIds);
}
