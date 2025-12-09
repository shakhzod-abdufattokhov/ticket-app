package uz.shaxzod.ticketapp.service;

import uz.shaxzod.ticketapp.models.requestDto.CategoryRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.CategoryResponse;

import java.util.List;

public interface SeatCategoryService {
    ApiResponse<String> create(CategoryRequest request);
    ApiResponse<Void> update(String categoryId, CategoryRequest request);
    ApiResponse<List<CategoryResponse>> getAll(String venueId);
    ApiResponse<CategoryResponse> getById(String categoryId);
    ApiResponse<Void> delete(String categoryId);

}
