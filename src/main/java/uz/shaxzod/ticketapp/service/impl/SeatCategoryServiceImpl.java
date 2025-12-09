package uz.shaxzod.ticketapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.shaxzod.ticketapp.exceptions.CustomNotFoundException;
import uz.shaxzod.ticketapp.mapper.CategoryMapper;
import uz.shaxzod.ticketapp.models.entity.SeatCategory;
import uz.shaxzod.ticketapp.models.entity.Venue;
import uz.shaxzod.ticketapp.models.requestDto.CategoryRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.CategoryResponse;
import uz.shaxzod.ticketapp.repository.SeatCategoryRepository;
import uz.shaxzod.ticketapp.repository.VenueRepository;
import uz.shaxzod.ticketapp.service.SeatCategoryService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeatCategoryServiceImpl implements SeatCategoryService {
    private final SeatCategoryRepository categoryRepository;
    private final VenueRepository venueRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public ApiResponse<String> create(CategoryRequest request) {
        log.info("Create Category request with body {}", request);
        Venue venue = venueRepository.findById(request.getVenueId()).orElseThrow(
                () -> new CustomNotFoundException("Venue not found with id: " + request.getVenueId()));

        SeatCategory category = categoryMapper.toEntity(request, venue);
        category = categoryRepository.save(category);

        return ApiResponse.success(category.getId(), "Created successfully");
    }

    @Override
    public ApiResponse<Void> update(String categoryId, CategoryRequest request) {
        log.info("Update Category request: {}", request.toString());
        SeatCategory seatCategory = categoryRepository.findById(categoryId).orElseThrow(
                () -> new CustomNotFoundException("Category not found with id: " + categoryId));

        seatCategory = categoryMapper.toUpdateEntity(seatCategory, request);
        categoryRepository.save(seatCategory);
        return ApiResponse.success("Updated successfully");
    }

    @Override
    public ApiResponse<List<CategoryResponse>> getAll(String venueId) {
        log.info("Get all Category request with venueId {}", venueId);
        boolean exists = venueRepository.existsById(venueId);
        if (!exists) {
            throw new CustomNotFoundException("Venue doesn't exist with id: " + venueId);
        }
        List<SeatCategory> seatCategories = categoryRepository.findAllByVenueId(venueId);
        List<CategoryResponse> categoryResponses = categoryMapper.toResponseList(seatCategories);
        return ApiResponse.success(categoryResponses);
    }

    @Override
    public ApiResponse<CategoryResponse> getById(String categoryId) {
        log.info("Get Category by id: {}", categoryId);
        SeatCategory seatCategory = categoryRepository.findById(categoryId).orElseThrow(
                () -> new CustomNotFoundException("Category not found with id: " + categoryId));

        CategoryResponse categoryResponse = categoryMapper.toResponse(seatCategory);
        return ApiResponse.success(categoryResponse);
    }

    @Override
    public ApiResponse<Void> delete(String categoryId) {
        log.info("Delete category with id: {}", categoryId);
        boolean exists = categoryRepository.existsById(categoryId);
        if (!exists) {
            throw new CustomNotFoundException("Category does not exist with id: " + categoryId);
        }
        categoryRepository.deleteById(categoryId);
        return ApiResponse.success("Deleted successfully");
    }
}
