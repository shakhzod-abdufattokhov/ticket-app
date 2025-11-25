package uz.shaxzod.ticketapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.shaxzod.ticketapp.exceptions.CustomBadRequestException;
import uz.shaxzod.ticketapp.exceptions.CustomNotFoundException;
import uz.shaxzod.ticketapp.mapper.VenueMapper;
import uz.shaxzod.ticketapp.models.entity.Venue;
import uz.shaxzod.ticketapp.models.requestDto.VenueRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.PaginationResponse;
import uz.shaxzod.ticketapp.models.responseDto.VenueResponse;
import uz.shaxzod.ticketapp.repository.VenueRepository;
import uz.shaxzod.ticketapp.service.VenueService;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {
    private final VenueRepository venueRepository;
    private final VenueMapper venueMapper;

    @Override
    public ApiResponse<String> create(VenueRequest request) {
        log.info("Service - Creating Venue request: {}", request);

        Venue venue = venueMapper.toEntity(request);
        log.info("Service - Venue saved successfully");
        return ApiResponse.success(venue.getId(), "Created successfully");
    }

    @Override
    public ApiResponse<VenueResponse> get(String id) {
        log.info("Service - Get venue request: {}", id);

        Venue venue = venueRepository.findById(id).orElseThrow(
                () -> new CustomNotFoundException("Venue not found with id: " + id));
        VenueResponse response = venueMapper.toResponse(venue);

        log.info("Service - Venue found: {}", response);
        return ApiResponse.success(response);
    }

    @Override
    public ApiResponse<PaginationResponse> getAll(Pageable pageable) {
        log.info("Service - Get all venue request");

        Page<Venue> venues = venueRepository.findAll(pageable);
        if (venues.isEmpty()) {
            log.info("Service - Venues are not exist");
            return ApiResponse.success(new PaginationResponse(), "Venues are not exist");
        }

        List<VenueResponse> venueResponses = venueMapper.toResponseList(venues.getContent());
        PaginationResponse response = new PaginationResponse(venues.getNumberOfElements(), venues.getTotalPages(), venueResponses);
        log.info("Service - Venues found");
        return ApiResponse.success(response);
    }

    @Override
    public ApiResponse<VenueResponse> patch(String id, Map<String, Object> fields) {
        log.info("Service - Patch update request with id {} and values {}", id, fields);
        Venue venue = venueRepository.findById(id).orElseThrow(
                () -> new CustomNotFoundException("Venue not found with id: " + id));

        fields.forEach((key, value) -> {
            switch (key) {
                case "name" -> {
                    String name = (String) value;
                    venue.setName(name);
                }
                case "city" -> {
                    String city = (String) value;
                    venue.setCity(city);
                }
                case "address" -> {
                    String address = (String) value;
                    venue.setAddress(address);
                }
                case "phoneNumber" -> {
                    String phoneNumber = (String) value;
                    isPhoneNumValid(phoneNumber);
                    venue.setPhoneNumber(phoneNumber);
                }
                default -> {
                    log.error("Given key doesn't match any field of venue: {}", key);
                    throw new CustomBadRequestException("Key doesn't match: " + key);
                }
            }
        });
        VenueResponse response = venueMapper.toResponse(venue);
        log.info("Service - Venue patch updated successfully");
        return ApiResponse.success(response);
    }

    @Override
    public ApiResponse<VenueResponse> update(String id, VenueRequest request) {
        log.info("Service - Update Venue request {}", request);

        Venue venue = venueRepository.findById(id).orElseThrow(
                () -> new CustomNotFoundException("Venue not found with id: " + id));
        Venue updatedVenue = venueMapper.toEntity(request);
        updatedVenue.setId(venue.getId());
        updatedVenue = venueRepository.save(updatedVenue);
        VenueResponse response = venueMapper.toResponse(updatedVenue);

        log.info("Service - Venue updated successfully");
        return ApiResponse.success(response);
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        log.info("Service Venue delete request with id: {}", id);

        venueRepository.findById(id).orElseThrow(
                () -> new CustomNotFoundException("Venue not found with id: " + id));

        venueRepository.deleteById(id);
        return ApiResponse.success("Deleted successfully");
    }

    private void isPhoneNumValid(String phoneNumber) {
        //Todo Write regex patter for validating phoneNum
    }
}
