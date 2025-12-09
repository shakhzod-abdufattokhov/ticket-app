package uz.shaxzod.ticketapp.models.entity;

import jakarta.persistence.*;
import lombok.*;


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
    private String type;
    private Long price;
}
