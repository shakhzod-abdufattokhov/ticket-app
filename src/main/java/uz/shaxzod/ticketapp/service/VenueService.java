package uz.shaxzod.ticketapp.service;

import org.springframework.data.domain.Pageable;
import uz.shaxzod.ticketapp.models.requestDto.VenueRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.PaginationResponse;
import uz.shaxzod.ticketapp.models.responseDto.VenueResponse;

import java.util.List;
import java.util.Map;

public interface VenueService {
    ApiResponse<VenueResponse> create(VenueRequest request);
    ApiResponse<VenueResponse> get(Long id);
    ApiResponse<PaginationResponse> getAll(Pageable pageable);
    ApiResponse<VenueResponse> patch(Long id, Map<String, Object> values);
    ApiResponse<VenueResponse> update(Long id, VenueRequest request);
    ApiResponse<Void> delete(Long id);
    //get
    //getAll
    //delete
    //update

}
