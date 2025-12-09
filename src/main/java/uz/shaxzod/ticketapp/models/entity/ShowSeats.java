package uz.shaxzod.ticketapp.models.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.shaxzod.ticketapp.models.enums.SeatType;

import java.time.LocalDateTime;

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

    @ManyToOne
    private SeatCategory category;
    private Long price; // tiyin
    private boolean isOrdered;
    private LocalDateTime createdAt;

    @PrePersist
    private void onCreated(){
        this.createdAt = LocalDateTime.now();
        isOrdered = false;
    }

}
