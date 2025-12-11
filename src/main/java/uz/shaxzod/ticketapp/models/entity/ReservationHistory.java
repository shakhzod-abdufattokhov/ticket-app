package uz.shaxzod.ticketapp.models.entity;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.*;
import uz.shaxzod.ticketapp.models.enums.ReservStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class ReservationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private String showId;
    private String eventName;
    @Column(nullable = false)
    private String seatId;
    private String seatLabel;
    @Enumerated(EnumType.STRING)
    private ReservStatus currentStatus;
    @Enumerated(EnumType.STRING)
    private ReservStatus oldStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    private void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
