package uz.shaxzod.ticketapp.models.entity;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.shaxzod.ticketapp.models.enums.OrderStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Show show;
    @ManyToOne
    private Seat seat;
    private Long totalAmount;
    private OrderStatus status;
    private String idempotencyKey;
    private LocalDateTime createdAt;

    @PostConstruct
    private void created(){
        this.createdAt = LocalDateTime.now();
    }

}
