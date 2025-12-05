package uz.shaxzod.ticketapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.shaxzod.ticketapp.models.requestDto.ShowSeatsRequest;
import uz.shaxzod.ticketapp.repository.ShowSeatsRepository;

@RequiredArgsConstructor
@Service
public class ShowSeatsService {
    private final ShowSeatsRepository showSeatsRepository;

    public String create(ShowSeatsRequest request){

    }
}
