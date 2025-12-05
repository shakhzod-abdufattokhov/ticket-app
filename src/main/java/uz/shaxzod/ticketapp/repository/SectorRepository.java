package uz.shaxzod.ticketapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.shaxzod.ticketapp.models.entity.Sector;

import java.util.List;

@Repository
public interface SectorRepository extends JpaRepository<Sector, String> {
    List<Sector> getAllByVenueId(String venueId);

    boolean existsByVenueIdAndName(String venueId, String name);
}
