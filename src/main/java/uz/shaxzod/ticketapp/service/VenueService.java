package uz.shaxzod.ticketapp.service;

import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;

public interface VenueService {
    ApiResponse<String> create(VenueRequest request);
}
