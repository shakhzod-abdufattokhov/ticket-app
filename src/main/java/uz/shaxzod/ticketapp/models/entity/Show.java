package uz.shaxzod.ticketapp.models.entity;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Event event;
    private LocalDate startDay;
    private LocalTime startTime;
    private LocalTime endTime;
    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Seat> seats;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PostConstruct
    private void created() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void updated() {
        this.updatedAt = LocalDateTime.now();
    }

}
