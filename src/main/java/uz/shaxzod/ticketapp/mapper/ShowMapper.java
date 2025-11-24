package uz.shaxzod.ticketapp.mapper;

import org.springframework.stereotype.Component;
import uz.shaxzod.ticketapp.models.entity.Show;
import uz.shaxzod.ticketapp.models.requestDto.ShowRequest;
import uz.shaxzod.ticketapp.models.responseDto.ShowResponse;

@Component
public class ShowMapper {
    public Show toEntity(ShowRequest request) {
        return Show.builder()
                .basePrice(request.getBasePrice())
                .showDay(request.getDay())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();
    }

    public ShowResponse toResponse(Show show) {
        return ShowResponse.builder()
                .id(show.getId())
                .day(show.getShowDay())
                .startTime(show.getStartTime())
                .endTime(show.getEndTime())
                .build();
    }
}
