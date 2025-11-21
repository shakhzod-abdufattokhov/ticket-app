package uz.shaxzod.ticketapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.shaxzod.ticketapp.models.requestDto.VenueRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.PaginationResponse;
import uz.shaxzod.ticketapp.models.responseDto.VenueResponse;
import uz.shaxzod.ticketapp.service.VenueService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/venues")
public class VenueController {
    private final VenueService service;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VenueResponse>> get(@PathVariable Long id){
        ApiResponse<VenueResponse> response = service.get(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PaginationResponse>> getAll(@RequestParam(defaultValue = "0") Integer page,
                                                  @RequestParam(defaultValue = "10") Integer size){
        Pageable pageable = PageRequest.of(page, size);
        ApiResponse<PaginationResponse> response = service.getAll(pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<VenueResponse>> create(@RequestBody @Valid VenueRequest request){
        ApiResponse<VenueResponse> response = service.create(request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/patch-update/{id}")
    public ResponseEntity<ApiResponse<VenueResponse>> patchUpdate(@PathVariable Long id,
                                                  @RequestBody Map<String, Object> fields){
        ApiResponse<VenueResponse> response = service.patch(id, fields);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VenueResponse>> update(@PathVariable Long id,
                                             @RequestBody @Valid VenueRequest request){
        ApiResponse<VenueResponse> response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id){
        ApiResponse<Void> response = service.delete(id);
        return ResponseEntity.ok(response);
    }



}
