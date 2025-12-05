package uz.shaxzod.ticketapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.shaxzod.ticketapp.models.requestDto.SectorRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.SectorResponse;
import uz.shaxzod.ticketapp.service.SectorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sectors")
public class SectorController {
    private final SectorService sectorService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SectorResponse>> get(@PathVariable String id) {
        ApiResponse<SectorResponse> response = sectorService.get(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<SectorResponse>>> getAll(@RequestParam String venueId) {
        ApiResponse<List<SectorResponse>> response = sectorService.getAll(venueId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> create(@RequestBody SectorRequest request) {
        ApiResponse<String> response = sectorService.create(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SectorResponse>> update(@PathVariable String id,
                                                              @RequestBody SectorRequest request){
        ApiResponse<SectorResponse> response = sectorService.update(id, request);
        return ResponseEntity.ok( response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        ApiResponse<Void> response = sectorService.delete(id);
        return ResponseEntity.ok(response);
    }
}
