package uz.shaxzod.ticketapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.shaxzod.ticketapp.models.requestDto.ShowRequest;
import uz.shaxzod.ticketapp.models.requestDto.ShowSeatsRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.PaginationResponse;
import uz.shaxzod.ticketapp.models.responseDto.ShowResponse;
import uz.shaxzod.ticketapp.service.ShowService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shows")
public class ShowController {
    private final ShowService service;

    @PreAuthorize("permitAll()")
    @GetMapping()
    public ResponseEntity<ApiResponse<PaginationResponse>> getAllByEventID(@RequestParam String eventId,
                                                                           @RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        ApiResponse<PaginationResponse> response = service.getAllByEventId(eventId, pageable);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/valid")
    public ResponseEntity<ApiResponse<PaginationResponse>> getAllValidByEventID(@RequestParam String eventId,
                                                                           @RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        ApiResponse<PaginationResponse> response = service.getAllValidByEventId(eventId, pageable);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGINIZER')")
    @PostMapping
    public ResponseEntity<ApiResponse<String>> create(@RequestBody ShowRequest request){
        ApiResponse<String> response = service.create(request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGINIZER')")
    @PutMapping("/{id}/update/seats")
    public ResponseEntity<ApiResponse<Void>> updateShowSets(@PathVariable String id,
                                                      @RequestBody ShowSeatsRequest request){
        ApiResponse<Void> response = service.updateShowSeats(id, request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGINIZER')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ShowResponse>> update(@PathVariable String id,
                                                            @RequestBody ShowRequest request){
        ApiResponse<ShowResponse> response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGINIZER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        ApiResponse<Void> response = service.delete(id);
        return ResponseEntity.ok(response);
    }
}
