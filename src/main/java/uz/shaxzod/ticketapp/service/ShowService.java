package uz.shaxzod.ticketapp.service;


import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.ShowResponse;

public interface ShowService {
    ApiResponse<ShowResponse> create(ShowRequest request);
}
