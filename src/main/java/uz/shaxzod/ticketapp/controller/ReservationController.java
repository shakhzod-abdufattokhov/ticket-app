package uz.shaxzod.ticketapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.shaxzod.ticketapp.models.redis.Reservation;
import uz.shaxzod.ticketapp.models.requestDto.ReservationRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.repository.redis.ReservationRepository;
import uz.shaxzod.ticketapp.service.ReservationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")
public class ReservationController {
    private final ReservationService service;
    private final ReservationRepository reservationRepository;

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Long>> get(@PathVariable String id){
        ApiResponse<Long> response = service.get(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<ApiResponse<String>> create(@RequestBody ReservationRequest request){
        ApiResponse<String> response = service.create(request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("permitAll()")
    @PutMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancel(@PathVariable String id){
        ApiResponse<Void> response = service.cancel(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<Void> deleteAll(){
        reservationRepository.deleteAll();
        return ResponseEntity.ok(null);
    }
}
