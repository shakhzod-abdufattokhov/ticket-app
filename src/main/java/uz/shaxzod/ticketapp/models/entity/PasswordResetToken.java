package uz.shaxzod.ticketapp.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String tokenHash;
    private LocalDateTime expireAt;
    private boolean used;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
