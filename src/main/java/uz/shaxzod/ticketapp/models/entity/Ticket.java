package uz.shaxzod.ticketapp.models.entity;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.shaxzod.ticketapp.models.enums.TicketStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    private Order order;
    @Column(unique = true)
    private String ticketToken; // QR
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
    private LocalDate issuedAt;
    private LocalDateTime createdAt;

    @PrePersist
    private void onCreate(){
        this.issuedAt = LocalDate.now();
        this.createdAt = LocalDateTime.now();

    }



}
