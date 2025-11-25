package uz.shaxzod.ticketapp.service;

import org.springframework.data.domain.Pageable;
import uz.shaxzod.ticketapp.models.requestDto.VenueRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.PaginationResponse;
import uz.shaxzod.ticketapp.models.responseDto.VenueResponse;

import java.util.List;
import java.util.Map;

public interface VenueService {
    ApiResponse<String> create(VenueRequest request);
    ApiResponse<VenueResponse> get(String id);
    ApiResponse<PaginationResponse> getAll(Pageable pageable);
    ApiResponse<VenueResponse> patch(String id, Map<String, Object> values);
    ApiResponse<VenueResponse> update(String id, VenueRequest request);
    ApiResponse<Void> delete(String id);

}
