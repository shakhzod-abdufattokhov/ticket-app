package uz.shaxzod.ticketapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.shaxzod.ticketapp.models.requestDto.CreateSeatsRequest;
import uz.shaxzod.ticketapp.models.requestDto.SeatRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.service.SeatService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/seats")
public class SeatController {
    private final SeatService seatService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> create(@RequestBody @Valid SeatRequest request){
        ApiResponse<String> response = seatService.createSeat(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-with-row")
    public ResponseEntity<ApiResponse<List<String>>> createSeatsWithRow(@RequestBody @Valid CreateSeatsRequest request){
        ApiResponse<List<String>> response = seatService.createSeatsRow(request);
        return ResponseEntity.ok(response);
    }
}
