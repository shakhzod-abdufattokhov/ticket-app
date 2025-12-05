package uz.shaxzod.ticketapp.service;


import uz.shaxzod.ticketapp.models.requestDto.SectorRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.SectorResponse;

import java.util.List;

public interface SectorService {
    ApiResponse<String> create(SectorRequest request);
    ApiResponse<SectorResponse> get(String id);
    ApiResponse<List<SectorResponse>> getAll(String venueId);
    ApiResponse<SectorResponse> update(String id, SectorRequest request);
    ApiResponse<Void> delete(String id);
}
