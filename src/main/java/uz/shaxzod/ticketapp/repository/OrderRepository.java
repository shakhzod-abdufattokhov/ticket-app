package uz.shaxzod.ticketapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.shaxzod.ticketapp.models.entity.Order;
import uz.shaxzod.ticketapp.models.entity.Show;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> getAllByShow(Show show);

    List<Order> getAllByShowId(String showId);
}
