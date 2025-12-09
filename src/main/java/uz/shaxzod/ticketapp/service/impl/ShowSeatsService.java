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

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShowSeatsService {
    private final ShowSeatsRepository showSeatsRepository;
    private final SeatRepository seatRepository;
    private final SeatCategoryRepository seatCategoryRepository;

    public void create(Show show, ShowSeatsRequest request){
        log.info("Created ShowSeat request: {}", request);
        SeatCategory seatCategory = seatCategoryRepository.findById(request.getCategoryId()).orElseThrow(
                () -> new CustomNotFoundException("Seat category not found with id: "+ request.getCategoryId()));

        for (String seatId : request.getSeatIds()) {
            Optional<Seat> seat = seatRepository.findById(seatId);
            if(seat.isEmpty()){
                continue;
            }
            ShowSeats showSeats = new ShowSeats();
            showSeats.setPrice(request.getPrice());
            showSeats.setSeat(seat.get());
            showSeats.setOrdered(false);
            showSeats.setCategory(seatCategory);
            showSeats.setShow(show);

            showSeatsRepository.save(showSeats);
        }
    }


}
