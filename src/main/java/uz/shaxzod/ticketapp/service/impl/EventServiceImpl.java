package uz.shaxzod.ticketapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.shaxzod.ticketapp.exceptions.CustomBadRequestException;
import uz.shaxzod.ticketapp.exceptions.CustomNotFoundException;
import uz.shaxzod.ticketapp.mapper.EventMapper;
import uz.shaxzod.ticketapp.models.entity.Event;
import uz.shaxzod.ticketapp.models.entity.Venue;
import uz.shaxzod.ticketapp.models.enums.EventStatus;
import uz.shaxzod.ticketapp.models.enums.EventType;
import uz.shaxzod.ticketapp.models.filter.EventFilterDto;
import uz.shaxzod.ticketapp.models.requestDto.EventRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.EventDetailedResponse;
import uz.shaxzod.ticketapp.models.responseDto.EventPreview;
import uz.shaxzod.ticketapp.models.responseDto.PaginationResponse;
import uz.shaxzod.ticketapp.repository.EventRepository;
import uz.shaxzod.ticketapp.repository.VenueRepository;
import uz.shaxzod.ticketapp.service.EventService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final VenueRepository venueRepository;

    @Override
    public ApiResponse<Void> create(EventRequest request) {
        log.info("Service - Event create request {}", request.toString());
        Optional<Venue> venue = venueRepository.findById(request.getVenueId());
        if(venue.isEmpty()){
            throw new CustomNotFoundException("Venue not found with id: "+ request.getVenueId());
        }

        Event event = eventMapper.toEntity(request);
        event.setVenue(venue.get());

        eventRepository.save(event);
        log.info("Event saved successfully");
        return ApiResponse.success("Created successfully");
    }

    @Override
    public ApiResponse<EventDetailedResponse> getById(Long id) {
        log.info("Service - Event Get By id request {}", id);
        Venue venue = venueRepository.findById(id).orElseThrow(
                () -> new CustomNotFoundException("Venue not found with id: "+ id));
        EventDetailedResponse response = eventMapper.toDetailedResponse(venue);
        log.info("Event found with id: {}", id);
        return ApiResponse.success(response);
    }

    @Override
    public ApiResponse<PaginationResponse> getAll(Pageable pageable) {
        log.info("Service - Event get all request");
        Page<Event> eventsPage = eventRepository.findAll(LocalDate.now(), pageable);
        if(eventsPage.isEmpty()){
            log.info("Events are not exist in db");
            return ApiResponse.success(new PaginationResponse(), "Events are not exist");
        }

        List<EventPreview> eventPreviews = eventMapper.toPreviewList(eventsPage.getContent());
        PaginationResponse response = new PaginationResponse(
                eventsPage.getNumberOfElements(),
                eventsPage.getTotalPages(),
                eventPreviews);

        return ApiResponse.success(response);
    }

    @Override
    public ApiResponse<PaginationResponse> getAllByType(String type, Pageable pageable) {
        log.info("Service - Event get all by type request");
        if(!EventType.contains(type)){
            log.error("Event type is not valid {}", type);
            throw new CustomBadRequestException("Event type is not valid: "+ type);
        }
        Page<Event> eventsPage = eventRepository.findAllByType(EventType.from(type), pageable);
        if(eventsPage.isEmpty()){
            log.info("No Event found with type: {}", type);
            return ApiResponse.success(new PaginationResponse(), "No Data has found");
        }
        List<EventPreview> eventPreviews = eventMapper.toPreviewList(eventsPage.getContent());
        PaginationResponse response = new PaginationResponse(
                eventsPage.getNumberOfElements(),
                eventsPage.getTotalPages(),
                eventPreviews);

        return ApiResponse.success(response);
    }

    @Override
    public ApiResponse<Void> changeStatus(Long id, String oldStatus, String newStatus) {
        return null;
    }

    @Override
    public ApiResponse<PaginationResponse> search(EventFilterDto filterDto) {
        return null;
    }

    @Override
    public ApiResponse<EventPreview> update(Long id, EventRequest request) {
        return null;
    }

    @Override
    public ApiResponse<Void> delete(Long id) {
        return null;
    }
}
