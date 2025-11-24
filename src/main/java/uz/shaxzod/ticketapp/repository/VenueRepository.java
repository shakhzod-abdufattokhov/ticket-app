package uz.shaxzod.ticketapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.shaxzod.ticketapp.models.entity.Venue;

@Repository
public interface VenueRepository extends JpaRepository<Venue, String> {
}
