package uz.shaxzod.ticketapp.mapper;

import org.springframework.stereotype.Component;
import uz.shaxzod.ticketapp.models.entity.Event;
import uz.shaxzod.ticketapp.models.enums.EventStatus;
import uz.shaxzod.ticketapp.models.enums.EventType;
import uz.shaxzod.ticketapp.models.enums.Language;
import uz.shaxzod.ticketapp.models.requestDto.EventRequest;
import uz.shaxzod.ticketapp.models.responseDto.EventDetailedResponse;
import uz.shaxzod.ticketapp.models.responseDto.EventPreview;

import java.util.List;

@Component
public class EventMapper {


    public Event toEntity(EventRequest request) {
        return Event.builder()
                .title(request.getTitle() != null ? request.getTitle() : "")
                .description(request.getDescription() != null ? request.getDescription() : "")
                .status(EventStatus.SCHEDULED)
                .type(EventType.from(request.getType()))
                .language(request.getLanguage() == null ? Language.UZ : Language.from(request.getLanguage()))
                .minAge(request.getMinAge())
                .build();
    }

    public EventDetailedResponse toDetailedResponse(Event event) {
        return EventDetailedResponse.builder()
                .title(event.getTitle())
                .description(event.getDescription())
                .lowestPrice(!event.getShows().isEmpty() ? event.getShows().get(0).getBasePrice() : 0)
                .venueId(event.getVenue().getId())
                .status(event.getStatus().toString())
                .type(event.getType().toString())
                .language(event.getLanguage().toString())
                .minAge(event.getMinAge())
                .build();
    }

    public EventPreview toPreview(Event event){
        return EventPreview.builder()
                .id(event.getId())
                .title(event.getTitle())
                .startDate(!event.getShows().isEmpty() ? event.getShows().get(0).getShowDay() : null)
                .startTime(!event.getShows().isEmpty() ? event.getShows().get(0).getStartTime() : null)
                .endTime(!event.getShows().isEmpty() ? event.getShows().get(0).getEndTime() : null)
                .lowestPrice(!event.getShows().isEmpty() ? event.getShows().get(0).getBasePrice() : 0)
                .build();
    }

    public List<EventPreview> toPreviewList(List<Event> content) {
        return content.stream()
                .map(this::toPreview)
                .toList();
    }
}
