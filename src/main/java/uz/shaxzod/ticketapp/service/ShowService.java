package uz.shaxzod.ticketapp.service;


import org.springframework.data.domain.Pageable;
import uz.shaxzod.ticketapp.models.requestDto.ShowRequest;
import uz.shaxzod.ticketapp.models.requestDto.ShowSeatsRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.PaginationResponse;
import uz.shaxzod.ticketapp.models.responseDto.ShowResponse;


public interface ShowService {
    ApiResponse<String> create(ShowRequest request);
    ApiResponse<ShowResponse> get(String id);
    ApiResponse<PaginationResponse> getAllByEventId(String eventId, Pageable pageable);
    ApiResponse<PaginationResponse> getAllValidByEventId(String eventId, Pageable pageable);
    ApiResponse<ShowResponse> update(String id, ShowRequest request);
    ApiResponse<Void> delete(String id);

    ApiResponse<Void> updateShowSeats(String id, ShowSeatsRequest request);

}
