package uz.shaxzod.ticketapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.shaxzod.ticketapp.exceptions.CustomIllegalArgumentException;
import uz.shaxzod.ticketapp.exceptions.CustomNotFoundException;
import uz.shaxzod.ticketapp.models.entity.Seat;
import uz.shaxzod.ticketapp.models.entity.SeatCategory;
import uz.shaxzod.ticketapp.models.entity.Show;
import uz.shaxzod.ticketapp.models.entity.ShowSeats;
import uz.shaxzod.ticketapp.models.requestDto.ShowSeatsRequest;
import uz.shaxzod.ticketapp.repository.SeatCategoryRepository;
import uz.shaxzod.ticketapp.repository.SeatRepository;
import uz.shaxzod.ticketapp.repository.ShowSeatsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShowSeatsService {
    private final ShowSeatsRepository showSeatsRepository;
    private final SeatRepository seatRepository;
    private final SeatCategoryRepository seatCategoryRepository;

    public void update(Show show, ShowSeatsRequest request) {
        log.info("Create showSeats request: {}", request);
        SeatCategory category = seatCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CustomNotFoundException("Seat category not found: " + request.getCategoryId()));

        List<String> seatIds = request.getSeatIds();

        List<ShowSeats> showSeats = showSeatsRepository.findAllBySeatIdIn(seatIds);

        if (showSeats.size() != seatIds.size()) {
            throw new CustomNotFoundException("Some seats were not found");
        }

        log.info("All found showSeats: {}", showSeats);
        for (ShowSeats showSeat : showSeats) {
            showSeat.setCategory(category);
            showSeat.setPrice(request.getPrice() * 100); //Converted to tiyin
        }

        validateSeatsAndShowSeats(show, request, showSeats);
        showSeatsRepository.saveAll(showSeats);
    }

    public void create(Show show, List<Seat> seats){
        log.info("Adding seats and show to ShowSeats");
        List<ShowSeats> showSeats = seats.stream()
                .map(seat -> ShowSeats.builder()
                        .show(show)
                        .seat(seat)
                        .isOrdered(false)
                        .build())
                .toList();
        showSeatsRepository.saveAll(showSeats);
    }


//    public void update(String showId, ShowSeatsRequest request) {
//        log.info("Update showSeats request: {}", request);
//        List<ShowSeats> showSeats = showSeatsRepository.findByShowIdAndSeatIdIn(
//                showId,
//                request.getSeatIds()
//        );
//
//        if (showSeats.isEmpty()) {
//            throw new CustomNotFoundException("No seats found for update");}
//
//        SeatCategory category = seatCategoryRepository.findById(request.getCategoryId())
//                .orElseThrow(() -> new CustomNotFoundException("Seat category not found: " + request.getCategoryId()));
//
//        for (ShowSeats ss : showSeats) {
//            ss.setPrice(request.getPrice());
//            ss.setCategory(category);
//        }
//        showSeatsRepository.saveAll(showSeats);
//    }

    public List<ShowSeats> getAllByShowId(String showId){
        log.info("Getting all showSeats by showId, {}", showId);
        return showSeatsRepository.findByShowId(showId);
    }


    private void validateSeatsAndShowSeats(Show show, ShowSeatsRequest request, List<ShowSeats> seats) {
//        if (seats.size() != request.getSeatIds().size()) {
//            throw new CustomNotFoundException("Some seats not found");
//        }

       /* List<ShowSeats> existing = showSeatsRepository.findByShowIdAndSeatIdIn(
                show.getId(), request.getSeatIds()
        );

        if (!existing.isEmpty()) {
            throw new CustomIllegalArgumentException("Some seats already assigned to this show");
        }*/
    }

}
