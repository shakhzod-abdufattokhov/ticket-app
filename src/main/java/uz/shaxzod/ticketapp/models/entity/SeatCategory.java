package uz.shaxzod.ticketapp.models.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.shaxzod.ticketapp.models.enums.SeatType;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SeatCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    private Venue venue;
    @Enumerated(EnumType.STRING)
    private SeatType type;
    private Long price;
}
