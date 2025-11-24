package uz.shaxzod.ticketapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.shaxzod.ticketapp.models.entity.Show;

import java.util.UUID;

public interface ShowRepository extends JpaRepository<Show, String> {
}
