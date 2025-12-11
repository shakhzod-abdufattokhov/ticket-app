package uz.shaxzod.ticketapp.models.redis;

import jakarta.persistence.Id;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@RedisHash(value = "Reservations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Reservation implements Serializable, Comparable<Reservation> {
    @Id
    private String id;
    @Indexed
    private String showId;
    @Indexed
    private String seatId;
    private String userId;
    @TimeToLive(unit = TimeUnit.MINUTES)
    private Long lifeTimeInMin;

    @Override
    public int compareTo(@NotNull Reservation o) {
        return this.compareTo(o);
    }
}
