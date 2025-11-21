package uz.shaxzod.ticketapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.shaxzod.ticketapp.models.entity.Event;
import uz.shaxzod.ticketapp.models.enums.EventType;

import java.time.LocalDate;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    @Query("""
            select e from Event e
            left join fetch e.shows s
            where s.showDay >= :now and e.type = :type
            """)
    Page<Event> findAllByType(EventType type,LocalDate date, Pageable pageable);
    @Query("""
            select e from Event e
            left join fetch e.shows s
            where s.showDay >= :now
            """)
    Page<Event> findAll(LocalDate now, Pageable pageable);
}
