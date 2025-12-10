package uz.shaxzod.ticketapp.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    private Venue venue;
    @Column(nullable = false)
    private Integer row;
    @Column(nullable = false)
    private Integer number;
    private String seatLabel;
    @ManyToOne
    private SeatCategory category;
    @ManyToOne
    private Sector sector;
    @OneToMany
    private List<ShowSeats> showSeats;
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
