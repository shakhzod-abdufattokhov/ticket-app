package uz.shaxzod.ticketapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.shaxzod.ticketapp.models.requestDto.CategoryRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.CategoryResponse;
import uz.shaxzod.ticketapp.service.SeatCategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class SeatCategoryController {
    private final SeatCategoryService service;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> get(@PathVariable String id){
        ApiResponse<CategoryResponse> response = service.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAll(@RequestParam String venueId){
        ApiResponse<List<CategoryResponse>> response = service.getAll(venueId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> create(@RequestBody CategoryRequest request){
        ApiResponse<String> response = service.create(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable String id,
                                                    @RequestBody CategoryRequest request){
        ApiResponse<Void> response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id){
        ApiResponse<Void> response = service.delete(id);
        return ResponseEntity.ok(response);
    }
}
