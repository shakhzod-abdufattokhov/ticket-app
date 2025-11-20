package uz.shaxzod.ticketapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.shaxzod.ticketapp.models.entity.Event;
import uz.shaxzod.ticketapp.models.enums.EventType;

import java.time.LocalDate;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    Page<Event> findAllByType(EventType type, Pageable pageable);
    @Query("""
            select e from Event e
            left join fetch e.shows s
            on s.showDay >= :now
            """)
    Page<Event> findAll(LocalDate now, Pageable pageable);
}
