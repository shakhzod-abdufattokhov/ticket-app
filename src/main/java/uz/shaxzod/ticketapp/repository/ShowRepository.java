package uz.shaxzod.ticketapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.shaxzod.ticketapp.models.entity.Show;

public interface ShowRepository extends JpaRepository<Show, String> {
    Page<Show> findAllByEventIdOrderByShowDay(String eventId, Pageable pageable);
}
