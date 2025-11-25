package uz.shaxzod.ticketapp.service.impl;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.shaxzod.ticketapp.exceptions.CustomBadRequestException;
import uz.shaxzod.ticketapp.exceptions.CustomNotFoundException;
import uz.shaxzod.ticketapp.mapper.EventMapper;
import uz.shaxzod.ticketapp.models.entity.Event;
import uz.shaxzod.ticketapp.models.entity.Show;
import uz.shaxzod.ticketapp.models.entity.Venue;
import uz.shaxzod.ticketapp.models.enums.EventStatus;
import uz.shaxzod.ticketapp.models.enums.EventType;
import uz.shaxzod.ticketapp.models.enums.Language;
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
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    private static final Integer GAP_TIME = 2;

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final VenueRepository venueRepository;

    @Override
    public ApiResponse<String> create(EventRequest request) {
        log.info("Service - Event create request {}", request.toString());
        Venue venue = venueRepository.findById(request.getVenueId()).orElseThrow(
                () -> new CustomNotFoundException("Venue not found with id: " + request.getVenueId()));
        if (!EventType.contains(request.getType())) {
            throw new CustomBadRequestException("No Event type like: " + request.getType());
        }

        Event event = eventMapper.toEntity(request);
        event.setVenue(venue);

        Event saved = eventRepository.save(event);
        log.info("Event saved successfully");
        return ApiResponse.success(saved.getId() ,"Created successfully");
    }

    @Override
    public ApiResponse<EventDetailedResponse> getById(String id) {
        log.info("Service - Event Get By id request {}", id);
        Event event = eventRepository.findById(id).orElseThrow(
                () -> new CustomNotFoundException("Event not found with id: " + id));
        EventDetailedResponse response = eventMapper.toDetailedResponse(event);
        log.info("Event found with id: {}, {}", id, response.toString());
        return ApiResponse.success(response);
    }

    @Override
    public ApiResponse<PaginationResponse> getAll(Pageable pageable) {
        log.info("Service - Event get all request");
        Page<Event> eventsPage = eventRepository.findAll(pageable);
        if (eventsPage.isEmpty()) {
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
    public ApiResponse<PaginationResponse> getAllValid(Pageable pageable) {
        log.info("Service - Event get all request");
        Page<Event> eventsPage = eventRepository.findAll(LocalDate.now(), pageable);
        if (eventsPage.isEmpty()) {
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
        if (!EventType.contains(type)) {
            log.error("Event type is not valid {}", type);
            throw new CustomBadRequestException("Event type is not valid: " + type);
        }
        Page<Event> eventsPage = eventRepository.findAllByType(EventType.from(type), LocalDate.now(), pageable);
        if (eventsPage.isEmpty()) {
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
    public ApiResponse<Void> changeStatus(String id, String currentStatus, String newStatus) {
        log.info("Service - Changing status of event - {}, from {} to {}", id, currentStatus, newStatus);
        if (!EventStatus.contains(currentStatus) && !EventStatus.contains(newStatus)) {
            throw new CustomBadRequestException("One of the Status is not valid");
        }
        if (currentStatus.equalsIgnoreCase(newStatus)) {
            return ApiResponse.success("Status updated successfully");
        }
        Event event = eventRepository.findById(id).orElseThrow(
                () -> new CustomNotFoundException("Event not found with id: " + id));

        if (!event.getStatus().toString().equalsIgnoreCase(currentStatus)) {
            throw new CustomBadRequestException("CurrentStatus is not equal to event's current status");
        }

        event.setStatus(EventStatus.valueOf(newStatus.toUpperCase()));
        eventRepository.save(event);
        return ApiResponse.success("Status updated successfully");
    }

    @Override
    public ApiResponse<PaginationResponse> search(EventFilterDto filterDto) {
        log.info("Searching Event request {}", filterDto.toString());

        Specification<Event> spec = getEventSpecification(filterDto);

        Pageable pageable = PageRequest.of(filterDto.getPage(), filterDto.getSize());
        Page<Event> page = eventRepository.findAll(spec, pageable);
        List<EventPreview> previewList = eventMapper.toPreviewList(page.getContent());

        PaginationResponse response = new PaginationResponse(page.getNumberOfElements(), page.getTotalPages(), previewList);
        log.info("Search result has found");
        return ApiResponse.success(response);
    }

    @Override
    public ApiResponse<EventPreview> update(String id, EventRequest request) {
        log.info("Event update request {}", request.toString());
        Event event = eventRepository.findById(id).orElseThrow(
                () -> new CustomNotFoundException("Event not found with id: "+ id));
        Venue venue = venueRepository.findById(request.getVenueId()).orElseThrow(
                () -> new CustomNotFoundException("Venue not found with id: " + request.getVenueId()));

        if (!EventType.contains(request.getType())) {
            log.error("Event type is not valid {}", request.getType());
            throw new CustomBadRequestException("Event type not valid: " + request.getType());
        }

        Event newEvent = eventMapper.toEntity(request);
        newEvent.setId(event.getId());
        newEvent.setVenue(venue);

        newEvent = eventRepository.save(newEvent);

        EventPreview response = eventMapper.toPreview(newEvent);
        log.info("Event updated successfully");
        return ApiResponse.success(response);
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        boolean exists = eventRepository.existsById(id);
        if(!exists){
            throw new CustomNotFoundException("Event not found with id: "+id);
        }

        eventRepository.deleteById(id);
        return ApiResponse.success("Deleted successfully");
    }

    private Specification<Event> getEventSpecification(EventFilterDto filterDto) {
        Specification<Event> spec = (root, query, cb) -> cb.conjunction();

        if (filterDto.getSearch() != null && !filterDto.getSearch().isBlank()) {
            String keyword = "%" + filterDto.getSearch().toLowerCase() + "%";

            spec = spec.and((root, query, cb) ->
                    cb.or(cb.like(cb.lower(root.get("title")), keyword),
                          cb.like(cb.lower(root.get("description")), keyword)));
        }

        if (filterDto.getAge() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("minAge"), filterDto.getAge()));
        }

        if (filterDto.getVenueId() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("venue").get("id"), filterDto.getVenueId())
            );
        }

        if (filterDto.getLanguage() != null && !filterDto.getLanguage().isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("language"), Language.valueOf(filterDto.getLanguage().toUpperCase()))
            );
        }

        if (filterDto.getDate() != null) {
            spec = spec.and((root, query, cb) -> {
                Join<Event, Show> showJoin = root.join("shows", JoinType.LEFT);
                return cb.equal(showJoin.get("showDay"), filterDto.getDate());
            });
        }

        if (filterDto.getTime() != null) {
            spec = spec.and((root, query, cb) -> {
                Join<Event, Show> showJoin = root.join("shows", JoinType.LEFT);
                return cb.greaterThanOrEqualTo(showJoin.get("startTime"), filterDto.getTime());
            });
        }
        return spec;
    }
}
