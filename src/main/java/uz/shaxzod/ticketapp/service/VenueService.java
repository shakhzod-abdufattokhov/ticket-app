package uz.shaxzod.ticketapp.service;

import uz.shaxzod.ticketapp.models.requestDto.VenueRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.VenueResponse;

import java.util.List;
import java.util.Map;

public interface VenueService {
    ApiResponse<Void> create(VenueRequest request);
    ApiResponse<VenueResponse> get(Long id);
    ApiResponse<List<VenueResponse>> getAll();
    ApiResponse<Void> patch(Long id, Map<String, Object> values);
    ApiResponse<VenueResponse> update(VenueRequest request, Long id);
    ApiResponse<Void> delete(Long id);
    //get
    //getAll
    //delete
    //update

}
