package uz.shaxzod.ticketapp.models.entity;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    private Venue venue;
    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ShowSeats> showSeats;
    private LocalDate showDay;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long basePrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
