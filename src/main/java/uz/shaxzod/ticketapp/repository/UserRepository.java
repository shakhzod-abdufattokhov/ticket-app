package uz.shaxzod.ticketapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.shaxzod.ticketapp.models.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
