package uz.shaxzod.ticketapp.service;

import uz.shaxzod.ticketapp.models.entity.Seat;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.SeatResponse;

import java.util.List;

public interface SeatService {
    ApiResponse<List<SeatResponse>> getByShowId(String showId);
}
