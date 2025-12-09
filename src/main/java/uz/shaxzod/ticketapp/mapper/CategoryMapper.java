package uz.shaxzod.ticketapp.mapper;

import org.springframework.stereotype.Component;
import uz.shaxzod.ticketapp.models.entity.SeatCategory;
import uz.shaxzod.ticketapp.models.entity.Venue;
import uz.shaxzod.ticketapp.models.requestDto.CategoryRequest;
import uz.shaxzod.ticketapp.models.responseDto.CategoryResponse;

import java.util.List;

@Component
public class CategoryMapper {
    public SeatCategory toEntity(CategoryRequest request, Venue venue) {
        return SeatCategory.builder()
                .price(request.getPrice() * 100)  //converted to tiyin
                .type(request.getType().toUpperCase())
                .venue(venue)
                .build();
    }

    public SeatCategory toUpdateEntity(SeatCategory seatCategory, CategoryRequest request) {
        seatCategory.setPrice(request.getPrice() * 100);
        seatCategory.setType(request.getType().toUpperCase());
        return seatCategory;
    }

    public CategoryResponse toResponse(SeatCategory seatCategory) {
        return CategoryResponse.builder()
                .id(seatCategory.getId())
                .price(seatCategory.getPrice())
                .type(seatCategory.getType())
                .build();
    }

    public List<CategoryResponse> toResponseList(List<SeatCategory> seatCategories) {
        return seatCategories.stream()
                .map(this::toResponse)
                .toList();
    }
}
