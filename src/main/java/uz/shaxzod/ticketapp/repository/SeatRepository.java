package uz.shaxzod.ticketapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.shaxzod.ticketapp.models.entity.Seat;
import uz.shaxzod.ticketapp.models.entity.Venue;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, String> {
    boolean existsBySectionAndRow(String section, Integer row);

    boolean existsByVenueIdAndSectionAndRow(String venueId, String section, Integer row);

    List<Seat> findByVenue(Venue venue);

    List<Seat> findByVenueId(String venueId);

    void deleteAllByVenueIdAndRow(String venueId, Integer row);

    boolean existsAllById(String id);
}
