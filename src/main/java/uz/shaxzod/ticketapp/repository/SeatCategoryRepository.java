package uz.shaxzod.ticketapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.shaxzod.ticketapp.models.entity.SeatCategory;

@Repository
public interface SeatCategoryRepository extends JpaRepository<SeatCategory, String> {
}
