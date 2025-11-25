package uz.shaxzod.ticketapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.shaxzod.ticketapp.models.filter.EventFilterDto;
import uz.shaxzod.ticketapp.models.requestDto.EventRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.EventDetailedResponse;
import uz.shaxzod.ticketapp.models.responseDto.EventPreview;
import uz.shaxzod.ticketapp.models.responseDto.PaginationResponse;
import uz.shaxzod.ticketapp.service.EventService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {
    private final EventService service;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EventDetailedResponse>> get(@PathVariable String id) {
        ApiResponse<EventDetailedResponse> response = service.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PaginationResponse>> getAll(@RequestParam(defaultValue = "0") Integer page,
                                                                  @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        ApiResponse<PaginationResponse> response = service.getAll(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/valid")
    public ResponseEntity<ApiResponse<PaginationResponse>> getAllValid(@RequestParam(defaultValue = "0") Integer page,
                                                                  @RequestParam(defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        ApiResponse<PaginationResponse> response = service.getAllValid(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-type")
    public ResponseEntity<ApiResponse<PaginationResponse>> getAllByType(@RequestParam(defaultValue = "0") Integer page,
                                                                        @RequestParam(defaultValue = "10") Integer size,
                                                                        @RequestParam String type) {
        Pageable pageable = PageRequest.of(page, size);
        ApiResponse<PaginationResponse> response = service.getAllByType(type, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PaginationResponse>> search(@ModelAttribute EventFilterDto filterDto){
        ApiResponse<PaginationResponse> response = service.search(filterDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> create(@RequestBody EventRequest request) {
        ApiResponse<String> response = service.create(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EventPreview>> update(@PathVariable String id,
                                                            @RequestBody EventRequest request){
        ApiResponse<EventPreview> response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/change-status")
    public ResponseEntity<ApiResponse<Void>> changeStatus(@PathVariable String id,
                                                          @RequestParam String oldStatus,
                                                          @RequestParam String newStatus){
        ApiResponse<Void> response = service.changeStatus(id, oldStatus, newStatus);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        ApiResponse<Void> response = service.delete(id);
        return ResponseEntity.ok(response);
    }



}
