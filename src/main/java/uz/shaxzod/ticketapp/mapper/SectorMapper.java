package uz.shaxzod.ticketapp.mapper;

import org.springframework.stereotype.Component;
import uz.shaxzod.ticketapp.models.entity.Sector;
import uz.shaxzod.ticketapp.models.entity.Venue;
import uz.shaxzod.ticketapp.models.requestDto.SectorRequest;
import uz.shaxzod.ticketapp.models.responseDto.SectorResponse;

import java.util.List;

@Component
public class SectorMapper {
    public Sector toEntity(SectorRequest request, Venue venue) {
        return new Sector(request.getName().trim(), venue);
    }

    public SectorResponse toResponse(Sector sector) {
        return SectorResponse.builder()
                .id(sector.getId())
                .name(sector.getName())
                .venueId(sector.getVenue().getId())
                .build();
    }

    public List<SectorResponse> toResponseList(List<Sector> sectors) {
        return sectors.stream()
                .map(this::toResponse)
                .toList();
    }
}
