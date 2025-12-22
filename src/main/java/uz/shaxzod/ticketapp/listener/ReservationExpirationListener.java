package uz.shaxzod.ticketapp.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.stereotype.Component;
import uz.shaxzod.ticketapp.service.ReservationService;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReservationExpirationListener {
    private final ReservationService reservationService;


    @EventListener
    public void handleRedisKeyExpired(RedisKeyExpiredEvent<String> event) {
        log.info("Handle Redis Key Expired request: {}", event);

        byte[] id = event.getId();
        String reservationId = new String(id, StandardCharsets.UTF_8);
        String keyspace = event.getKeyspace();

        log.info("Reservation expired ->   keySpace={}, id={}", keyspace, reservationId);
        if(!keyspace.equals("reservation")){
            return;
        }

        reservationService.handleExpiration(reservationId);
    }

}
