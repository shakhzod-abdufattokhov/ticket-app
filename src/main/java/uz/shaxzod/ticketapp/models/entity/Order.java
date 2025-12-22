package uz.shaxzod.ticketapp.models.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.shaxzod.ticketapp.models.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id")
    private Show show;
//    @ManyToMany
//    private List<Seat> seat;
    @OneToMany
    private List<ShowSeats> showSeats;
    private Long totalAmount;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private String idempotencyKey;
    private LocalDateTime createdAt;

    @PrePersist
    private void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

}
