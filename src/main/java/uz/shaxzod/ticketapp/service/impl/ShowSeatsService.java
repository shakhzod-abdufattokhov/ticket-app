package uz.shaxzod.ticketapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.shaxzod.ticketapp.exceptions.CustomNotFoundException;
import uz.shaxzod.ticketapp.models.entity.Seat;
import uz.shaxzod.ticketapp.models.entity.SeatCategory;
import uz.shaxzod.ticketapp.models.entity.Show;
import uz.shaxzod.ticketapp.models.entity.ShowSeats;
import uz.shaxzod.ticketapp.models.requestDto.ShowSeatsRequest;
import uz.shaxzod.ticketapp.repository.SeatCategoryRepository;
import uz.shaxzod.ticketapp.repository.SeatRepository;
import uz.shaxzod.ticketapp.repository.ShowSeatsRepository;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShowSeatsService {
    private final ShowSeatsRepository showSeatsRepository;
    private final SeatRepository seatRepository;
    private final SeatCategoryRepository seatCategoryRepository;

    public void create(Show show, ShowSeatsRequest request) {
        log.info("Create showSeats request: {}", request);
        SeatCategory category = seatCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CustomNotFoundException("Seat category not found: " + request.getCategoryId()));

        List<Seat> seats = seatRepository.findAllById(request.getSeatIds());
        validateSeatsAndShowSeats(show, request, seats);

        List<ShowSeats> list = seats.stream()
                .map(seat -> ShowSeats.builder()
                        .show(show)
                        .seat(seat)
                        .category(category)
                        .price(request.getPrice())
                        .isOrdered(false)
                        .build()
                ).toList();

        showSeatsRepository.saveAll(list);
    }


    public void update(String showId, ShowSeatsRequest request) {
        log.info("Update showSeats request: {}", request);
        List<ShowSeats> showSeats = showSeatsRepository.findByShowIdAndSeatIdIn(
                showId,
                request.getSeatIds()
        );

        if (showSeats.isEmpty()) {
            throw new CustomNotFoundException("No seats found for update");}

        SeatCategory category = seatCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CustomNotFoundException("Seat category not found: " + request.getCategoryId()));

        for (ShowSeats ss : showSeats) {
            ss.setPrice(request.getPrice());
            ss.setCategory(category);
        }
        showSeatsRepository.saveAll(showSeats);
    }

    public List<ShowSeats> getAllByShowId(String showId){
        log.info("Getting all showSeats by showId, {}", showId);
        return showSeatsRepository.findByShowId(showId);
    }


    private void validateSeatsAndShowSeats(Show show, ShowSeatsRequest request, List<Seat> seats) {
        if (seats.size() != request.getSeatIds().size()) {
            throw new CustomNotFoundException("Some seats not found");
        }

        List<ShowSeats> existing = showSeatsRepository.findByShowIdAndSeatIdIn(
                show.getId(), request.getSeatIds()
        );

        if (!existing.isEmpty()) {
            throw new IllegalStateException("Some seats already assigned to this show");
        }
    }

}
