package uz.shaxzod.ticketapp.models.entity;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.shaxzod.ticketapp.models.enums.SeatType;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Venue venue;
    @ManyToOne
    private Show show;
    @Column(nullable = false)
    private String section;
    @Column(nullable = false)
    private String row;
    @Column(nullable = false)
    private Integer number;
    private SeatType type;
    private Long price;
    private String seatLabel;
    private boolean isOrdered;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PostConstruct
    private void created(){
        this.createdAt = LocalDateTime.now();
        this.isOrdered = false;
    }

    @PreUpdate
    private void updated(){
        this.updatedAt = LocalDateTime.now();
    }
}
