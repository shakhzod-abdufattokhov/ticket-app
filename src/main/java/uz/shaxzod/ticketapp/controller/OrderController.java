package uz.shaxzod.ticketapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.shaxzod.ticketapp.models.requestDto.OrderRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.OrderResponse;
import uz.shaxzod.ticketapp.service.OrderService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> get(@PathVariable String id){
        ApiResponse<OrderResponse> response = orderService.get(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get/by-show-id/{id}")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllByShowId(@PathVariable String id){
        ApiResponse<List<OrderResponse>> response = orderService.getAllByShowId(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    public ResponseEntity<ApiResponse<String>> create(@RequestBody @Valid OrderRequest request,
                                                      @RequestParam String paymentMethod){
        ApiResponse<String> response = orderService.create(request, paymentMethod);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancel(@PathVariable String id){
        ApiResponse<Void> response = orderService.cancel(id);
        return ResponseEntity.ok(response);
    }
}
