package uz.shaxzod.ticketapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.shaxzod.ticketapp.models.requestDto.CreateSeatsRequest;
import uz.shaxzod.ticketapp.models.requestDto.SeatDeleteRequest;
import uz.shaxzod.ticketapp.models.requestDto.SeatRequest;
import uz.shaxzod.ticketapp.models.requestDto.SeatUpdateRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.SeatResponse;
import uz.shaxzod.ticketapp.service.SeatService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/seats")
public class SeatController {
    private final SeatService seatService;

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SeatResponse>> getById(@PathVariable String id){
        ApiResponse<SeatResponse> response = seatService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGINIZER')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<SeatResponse>>> getAllByVenueId(@RequestParam String venueId){
        ApiResponse<List<SeatResponse>> response = seatService.getAll(venueId);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/by-show")
    public ResponseEntity<ApiResponse<List<SeatResponse>>> getAllByShow(@RequestParam String showId){
        ApiResponse<List<SeatResponse>> response = seatService.getAllByShowId(showId);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGINIZER')")
    @PostMapping
    public ResponseEntity<ApiResponse<String>> create(@RequestBody @Valid SeatRequest request){
        ApiResponse<String> response = seatService.createSeat(request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGINIZER')")
    @PostMapping("/create-with-row")
    public ResponseEntity<ApiResponse<List<String>>> createSeatsWithRow(@RequestBody @Valid CreateSeatsRequest request){
        ApiResponse<List<String>> response = seatService.createSeatsRow(request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGINIZER')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SeatResponse>> update(@PathVariable String id,
                                                            @RequestBody SeatUpdateRequest request){
        ApiResponse<SeatResponse> response = seatService.updateSeat(id, request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGINIZER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable String id){
        ApiResponse<Void> response = seatService.deleteSeat(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGINIZER')")
    @DeleteMapping("/delete-with-row")
    public ResponseEntity<ApiResponse<Void>> deleteSeatsRow(@RequestParam String venueId,
                                                            @RequestParam Integer row){
        ApiResponse<Void> response = seatService.deleteRowSeats(venueId, row);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGINIZER')")
    @DeleteMapping("/delete-by-ids")
    public ResponseEntity<ApiResponse<Void>> deleteSeatsByIds(@RequestBody SeatDeleteRequest request){
        ApiResponse<Void> response = seatService.deleteSeats(request);
        return ResponseEntity.ok(response);
    }
}
