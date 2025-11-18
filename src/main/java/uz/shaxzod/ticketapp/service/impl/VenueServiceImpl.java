package uz.shaxzod.ticketapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.shaxzod.ticketapp.models.requestDto.VenueRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.VenueResponse;
import uz.shaxzod.ticketapp.service.VenueService;

import java.util.List;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {
    @Override
    public ApiResponse<Void> create(VenueRequest request) {
        return null;
    }

    @Override
    public ApiResponse<VenueResponse> get(Long id) {
        return null;
    }

    @Override
    public ApiResponse<List<VenueResponse>> getAll() {
        return null;
    }

    @Override
    public ApiResponse<Void> patch(Long id, Map<String, Object> values) {
        return null;
    }

    @Override
    public ApiResponse<VenueResponse> update(VenueRequest request, Long id) {
        return null;
    }

    @Override
    public ApiResponse<Void> delete(Long id) {
        return null;
    }
}
