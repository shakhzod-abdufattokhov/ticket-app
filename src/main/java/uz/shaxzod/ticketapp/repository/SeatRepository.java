package uz.shaxzod.ticketapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.shaxzod.ticketapp.models.entity.Seat;
import uz.shaxzod.ticketapp.models.entity.Venue;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, String> {
    List<Seat> findByVenueId(String venueId);

    void deleteAllByVenueIdAndRow(String venueId, Integer row);

    boolean existsAllById(String id);

    boolean existsByVenueIdAndSectorIdAndRow(String venueId, String sectorId, Integer row);

    List<Seat> findAllByVenueId(String venueId);
}
