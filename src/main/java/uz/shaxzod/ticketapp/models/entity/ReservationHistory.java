package uz.shaxzod.ticketapp.models.entity;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.shaxzod.ticketapp.models.enums.ReservStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ReservationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long showId;
    private String showName;
    @Column(nullable = false)
    private Long seatId;
    private String seatLabel;
    @Enumerated(EnumType.STRING)
    private ReservStatus currentStatus;
    @Enumerated(EnumType.STRING)
    private ReservStatus oldStatus;
    private LocalDateTime createdAt;

    @PrePersist
    private void onUpdate(){
        this.createdAt = LocalDateTime.now();
    }
}
