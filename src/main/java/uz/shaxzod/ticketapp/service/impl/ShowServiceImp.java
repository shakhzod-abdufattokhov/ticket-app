package uz.shaxzod.ticketapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.shaxzod.ticketapp.exceptions.CustomBadRequestException;
import uz.shaxzod.ticketapp.exceptions.CustomNotFoundException;
import uz.shaxzod.ticketapp.mapper.ShowMapper;
import uz.shaxzod.ticketapp.models.entity.Event;
import uz.shaxzod.ticketapp.models.entity.Show;
import uz.shaxzod.ticketapp.models.entity.Venue;
import uz.shaxzod.ticketapp.models.requestDto.ShowRequest;
import uz.shaxzod.ticketapp.models.requestDto.ShowSeatsRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.PaginationResponse;
import uz.shaxzod.ticketapp.models.responseDto.ShowResponse;
import uz.shaxzod.ticketapp.repository.EventRepository;
import uz.shaxzod.ticketapp.repository.ShowRepository;
import uz.shaxzod.ticketapp.repository.VenueRepository;
import uz.shaxzod.ticketapp.service.ShowService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShowServiceImp implements ShowService {
    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final ShowRepository showRepository;
    private final ShowMapper showMapper;

    private static final Integer GAP_TIME = 2;
    private final ShowSeatsService showSeatsService;

    @Override
    public ApiResponse<String> create(ShowRequest request) {
        log.info("Service - Create show request {}", request.toString());
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new CustomNotFoundException("Event not found with id: " + request.getEventId()));

        Venue venue = venueRepository.findById(request.getVenueId()).orElseThrow(
                () -> new CustomNotFoundException("Venue not found with id: "+ request.getVenueId()));
        validateShowTime(request, event);

        Show show = showMapper.toEntity(request);
        show.setEvent(event);
        show.setVenue(venue);
        Show savedShow = showRepository.save(show);
        log.info("Show created successfully");
        return ApiResponse.success(savedShow.getId(), "Show created successfully");
    }

    @Override
    public ApiResponse<ShowResponse> get(String id) {
        log.info("Getting show with id: {}", id);
        Optional<Show> showOptional = showRepository.findById(id);
        if(showOptional.isEmpty()){
            log.error("Show not exist with id: {}", id);
            throw new CustomNotFoundException("Show not found with id: " + id);
        }
        ShowResponse response = showMapper.toResponse(showOptional.get());
        log.info("Show found {}", response.toString());
        return ApiResponse.success(response);
    }

    @Override
    public ApiResponse<PaginationResponse> getAllByEventId(String eventId, Pageable pageable) {
        log.info("Getting all Shows by Event ID request {}", eventId);
        isEventExist(eventId);

        Page<Show> eventsPage = showRepository.findAllByEventIdOrderByShowDay(eventId, pageable);
        if(eventsPage.isEmpty()){
            return ApiResponse.success(new PaginationResponse());
        }

        List<ShowResponse> responseList = showMapper.toResponseList(eventsPage.getContent());
        PaginationResponse paginationResponse = new PaginationResponse(
                eventsPage.getNumberOfElements(),
                eventsPage.getTotalPages(),
                responseList);
        log.info("Shows found with eventId");
        return ApiResponse.success(paginationResponse);
    }

    @Override
    public ApiResponse<PaginationResponse> getAllValidByEventId(String eventId, Pageable pageable) {
        log.info("Getting all valid Shows by Event ID request {}", eventId);
        isEventExist(eventId);

        Page<Show> showsPage = showRepository.findAllByEventIdOrderByShowDay(eventId, pageable);
        if(showsPage.isEmpty()){
            return ApiResponse.success(new PaginationResponse());
        }

        List<Show> shows = showsPage.getContent().stream()
                .filter(show -> (show.getShowDay().isAfter(LocalDate.now()) || show.getShowDay().equals(LocalDate.now())) )
                .toList();

        List<ShowResponse> showResponses = showMapper.toResponseList(shows);
        PaginationResponse paginationResponse = new PaginationResponse(
                showsPage.getNumberOfElements(),
                showsPage.getTotalPages(),
                showResponses);
        return ApiResponse.success(paginationResponse);
    }



    @Override
    public ApiResponse<ShowResponse> update(String id, ShowRequest request) {
         log.info("Show update request: {}", request.toString());
        Optional<Show> showOptional = showRepository.findById(id);
        if(showOptional.isEmpty()){
            log.error("Show not found with id: {}", id);
            throw new CustomNotFoundException("Show not found with id: "+ id);
        }

        Show show = showMapper.toEntity(request);
        show.setId(showOptional.get().getId());

        show = showRepository.save(show);
        ShowResponse response = showMapper.toResponse(show);
        return ApiResponse.success(response);
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        boolean exists = showRepository.existsById(id);
        if(!exists){
            log.error("Show not found with id: {}", id);
            throw new CustomNotFoundException("Show not found with id: "+ id);
        }
        showRepository.deleteById(id);
        return ApiResponse.success("Deleted successfully");
    }

    @Override
    public ApiResponse<Void> createShowSeats(String id, ShowSeatsRequest request) {
        log.info("Adding seats to show request: {}", request);
        Show show = showRepository.findById(id).orElseThrow(
                () -> new CustomNotFoundException("Show not found with id: " + id));

        showSeatsService.create(show,request);
        return ApiResponse.success("Seats are added successfully");
    }

    private void isEventExist(String eventId) {
        boolean exists = eventRepository.existsById(eventId);
        if(!exists){
            log.error("Event not found with id: {}", eventId);
            throw new CustomNotFoundException("Event not found with id: " + eventId);
        }
    }

    private static void validateShowTime(ShowRequest request, Event event) {
        if(request.getStartTime().isAfter(request.getEndTime()) || request.getStartTime().equals(request.getEndTime())){
            log.error("Show startTime must be before the endTime");
            throw new CustomBadRequestException("Show start time must be before the end time");
        }
        if( request.getDay().isBefore(LocalDate.now())){
            log.error("Show time is not valid, it must be future, not past");
            throw new CustomBadRequestException("Show time mustn't be in past");
        }

        if(request.getDay().equals(LocalDate.now()) && request.getStartTime().isBefore(LocalTime.now().plusHours(1))){
            log.error("Show time is not valid, it must be future, not past");
            throw new CustomBadRequestException("Show time mustn't be in past, respect 1 hour gap");
        }

        boolean conflict = event.getShows().stream()
                .filter(show -> show.getShowDay().equals(request.getDay()))
                .anyMatch(existing -> {
                    LocalTime exStart = existing.getStartTime();
                    LocalTime exEnd   = existing.getEndTime();

                    LocalTime exEndPlus1h = exEnd.plusHours(1);
                    LocalTime newEndPlus1h = request.getEndTime().plusHours(1);

                    return (!request.getStartTime().isAfter(exEndPlus1h) &&
                            !request.getStartTime().isBefore(exStart.minusHours(1))) &&
                            (!exStart.isAfter(newEndPlus1h) &&
                                    !exEnd.isBefore(request.getStartTime().minusHours(1)));
                });

        if (conflict) {
            log.error("Show time conflict detected for event {}", event.getId());
            throw new CustomBadRequestException(
                    "Show time invalid. New show overlaps or does not respect 1-hour gap    .");
        }
    }
}
