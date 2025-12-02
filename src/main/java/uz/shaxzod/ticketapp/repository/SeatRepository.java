package uz.shaxzod.ticketapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.shaxzod.ticketapp.models.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, String> {
    boolean existsBySectionAndRow(String section, Integer row);

    boolean existsByVenueIdAndSectionAndRow(String venueId, String section, Integer row);
}
