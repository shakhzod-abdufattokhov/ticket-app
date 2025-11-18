package uz.shaxzod.ticketapp.models.entity;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.shaxzod.ticketapp.models.enums.EventStatus;
import uz.shaxzod.ticketapp.models.enums.EventType;
import uz.shaxzod.ticketapp.models.enums.Language;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Venue venue;
    @Column(nullable = false)
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private EventStatus status;
    @Enumerated(EnumType.STRING)
    private EventType type;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Show> shows;
    @Column(nullable = false)
    private Language language; // show language, which language it holds
    @Column(nullable = false)
    private Integer minAge;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PostConstruct
    private void created() {
        this.createdAt = LocalDateTime.now();
        this.language = Language.UZ;
    }

    @PreUpdate
    private void updated() {
        this.updatedAt = LocalDateTime.now();
    }

}
