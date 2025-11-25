package uz.shaxzod.ticketapp.models.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.shaxzod.ticketapp.models.enums.SeatType;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowSeats {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @Enumerated(EnumType.STRING)
    private SeatType type;
    private Long price; // tiyin
    private boolean isOrdered;
    private LocalDateTime createdAt;

    @PrePersist
    private void onCreated(){
        this.createdAt = LocalDateTime.now();
        isOrdered = false;
    }

}
