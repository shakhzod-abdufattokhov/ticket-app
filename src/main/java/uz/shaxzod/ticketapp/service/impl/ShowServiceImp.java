package uz.shaxzod.ticketapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.shaxzod.ticketapp.exceptions.CustomNotFoundException;
import uz.shaxzod.ticketapp.mapper.ShowMapper;
import uz.shaxzod.ticketapp.models.entity.Event;
import uz.shaxzod.ticketapp.models.entity.Show;
import uz.shaxzod.ticketapp.models.requestDto.ShowRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.PaginationResponse;
import uz.shaxzod.ticketapp.models.responseDto.ShowResponse;
import uz.shaxzod.ticketapp.repository.EventRepository;
import uz.shaxzod.ticketapp.repository.ShowRepository;
import uz.shaxzod.ticketapp.service.ShowService;

import java.awt.print.Pageable;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShowServiceImp implements ShowService {
    private final EventRepository eventRepository;
    private final ShowRepository showRepository;
    private final ShowMapper showMapper;
    @Override
    public ApiResponse<Void> create(ShowRequest request) {
        log.info("Service - Create show request {}", request.toString());
        Optional<Event> event = eventRepository.findById(request.getEventId());
        if(event.isEmpty()){
            log.error("Event not exist with id: {}", request.getEventId());
            throw new CustomNotFoundException("Event not found with id: "+ request.getEventId());
        }
        Show show = showMapper.toEntity(request);
        show.setEvent(event.get());
        showRepository.save(show);
        log.info("Show created successfully");
        return ApiResponse.success("Show created successfully");
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

        return null;
    }

    @Override
    public ApiResponse<PaginationResponse> getAllValidByEventId(String eventId, Pageable pageable) {
        return null;
    }


    @Override
    public ApiResponse<Void> update(String id, ShowRequest request) {
        return null;
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        return null;
    }
}
