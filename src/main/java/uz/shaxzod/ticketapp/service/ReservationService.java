package uz.shaxzod.ticketapp.service;

import io.swagger.annotations.Api;
import uz.shaxzod.ticketapp.models.redis.Reservation;
import uz.shaxzod.ticketapp.models.requestDto.ReservationRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;

public interface ReservationService {
    ApiResponse<String> create(ReservationRequest request);
    ApiResponse<Long> get(String id);
    ApiResponse<Void> cancel(String id);
}
