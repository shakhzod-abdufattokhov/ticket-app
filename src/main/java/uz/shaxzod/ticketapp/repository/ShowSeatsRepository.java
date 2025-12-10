package uz.shaxzod.ticketapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.shaxzod.ticketapp.models.entity.ShowSeats;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowSeatsRepository extends JpaRepository<ShowSeats, String> {
    List<ShowSeats> findByShowId(String showId);

    List<ShowSeats> findAllBySeatIdIn(List<String> seatIds);
}
