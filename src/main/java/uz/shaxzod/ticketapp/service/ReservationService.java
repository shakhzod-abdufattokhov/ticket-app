package uz.shaxzod.ticketapp.service;

import uz.shaxzod.ticketapp.models.requestDto.ReservationRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;

public interface ReservationService {
    ApiResponse<String> create(ReservationRequest request);
    ApiResponse<Long> get(String id);
    ApiResponse<Void> cancel(String id);

    //Inner used method
    void handleExpiration(String reservationId);
}
