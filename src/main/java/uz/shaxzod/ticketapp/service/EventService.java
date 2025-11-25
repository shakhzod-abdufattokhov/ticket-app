package uz.shaxzod.ticketapp.service;

import org.springframework.data.domain.Pageable;
import uz.shaxzod.ticketapp.models.filter.EventFilterDto;
import uz.shaxzod.ticketapp.models.requestDto.EventRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.EventDetailedResponse;
import uz.shaxzod.ticketapp.models.responseDto.EventPreview;
import uz.shaxzod.ticketapp.models.responseDto.PaginationResponse;


import java.util.List;

public interface EventService {
    ApiResponse<String> create(EventRequest request);
    ApiResponse<EventDetailedResponse> getById(String id);
    ApiResponse<PaginationResponse> getAll(Pageable pageable);
    ApiResponse<PaginationResponse> getAllValid(Pageable pageable);
    ApiResponse<PaginationResponse> getAllByType(String type, Pageable pageable);
    ApiResponse<Void> changeStatus(String id, String oldStatus, String newStatus); //Cancel, Postpone
    ApiResponse<PaginationResponse> search(EventFilterDto filterDto);
    ApiResponse<EventPreview> update(String id, EventRequest request);
    ApiResponse<Void> delete(String id);
}