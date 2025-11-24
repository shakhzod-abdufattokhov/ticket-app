package uz.shaxzod.ticketapp.service;


import uz.shaxzod.ticketapp.models.requestDto.ShowRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.PaginationResponse;
import uz.shaxzod.ticketapp.models.responseDto.ShowResponse;

import java.awt.print.Pageable;

public interface ShowService {
    ApiResponse<Void> create(ShowRequest request);
    ApiResponse<ShowResponse> get(String id);
    ApiResponse<PaginationResponse> getAllByEventId(String eventId, Pageable pageable);
    ApiResponse<PaginationResponse> getAllValidByEventId(String eventId, Pageable pageable);
    ApiResponse<Void> update(String id, ShowRequest request);
    ApiResponse<Void> delete(String id);
}
