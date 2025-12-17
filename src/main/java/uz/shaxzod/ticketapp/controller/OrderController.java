package uz.shaxzod.ticketapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.shaxzod.ticketapp.models.requestDto.OrderRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.service.OrderService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> create(@RequestBody @Valid OrderRequest request,
                                                      @RequestParam String paymentMethod){
        ApiResponse<String> response = orderService.create(request, paymentMethod);
        return ResponseEntity.ok(response);
    }
}
