package uz.shaxzod.ticketapp.mapper;

import org.springframework.stereotype.Component;
import uz.shaxzod.ticketapp.models.entity.Venue;
import uz.shaxzod.ticketapp.models.requestDto.VenueRequest;
import uz.shaxzod.ticketapp.models.responseDto.VenueResponse;

import java.util.List;

@Component
public class VenueMapper {
    public Venue toEntity(VenueRequest request){
        return Venue.builder()
                .name(request.getName())
                .city(request.getCity())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }

    public VenueResponse toResponse(Venue venue){
        return VenueResponse.builder()
                .id(venue.getId())
                .name(venue.getName())
                .city(venue.getCity())
                .address(venue.getAddress())
                .phoneNumber(venue.getPhoneNumber())
                .build();
    }

    public List<VenueResponse> toResponseList(List<Venue> venues) {
        return venues.stream()
                .map(this::toResponse)
                .toList();
    }
}
