package uz.shaxzod.ticketapp.service.impl;

import jakarta.servlet.ServletConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.shaxzod.ticketapp.exceptions.CustomAlreadyExistException;
import uz.shaxzod.ticketapp.exceptions.CustomNotFoundException;
import uz.shaxzod.ticketapp.mapper.SectorMapper;
import uz.shaxzod.ticketapp.models.entity.Sector;
import uz.shaxzod.ticketapp.models.entity.Venue;
import uz.shaxzod.ticketapp.models.requestDto.SectorRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.SectorResponse;
import uz.shaxzod.ticketapp.repository.SectorRepository;
import uz.shaxzod.ticketapp.repository.VenueRepository;
import uz.shaxzod.ticketapp.service.SectorService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SectorServiceImpl implements SectorService {
    private final SectorRepository sectorRepository;
    private final VenueRepository venueRepository;
    private final SectorMapper sectorMapper;
    private final ServletConfig servletConfig;

    @Override
    public ApiResponse<String> create(SectorRequest request) {
        log.info("Create Sector request: {}", request.toString());
        Venue venue = venueRepository.findById(request.getVenueId()).orElseThrow(
                () -> new CustomNotFoundException("Venue not found with id: " + request.getVenueId()));

        checkNameIsUnique(request);

        Sector sector = sectorMapper.toEntity(request, venue);
        sector = sectorRepository.save(sector);
        return ApiResponse.success(sector.getId(), "Created successfully");
    }

    @Override
    public ApiResponse<SectorResponse> get(String id) {
        log.info("Getting sector with id request: {}", id);
        Sector sector = sectorRepository.findById(id).orElseThrow(
                () -> new CustomNotFoundException("Sector not found with id: "+ id));

        SectorResponse response = sectorMapper.toResponse(sector);
        return ApiResponse.success(response);
    }

    @Override
    public ApiResponse<List<SectorResponse>> getAll(String venueId) {
        log.info("Getting all Sector request with venueId: {}", venueId);
        boolean exists = venueRepository.existsById(venueId);
        if(!exists){
            throw new CustomNotFoundException("Venue not found with id: "+ venueId);
        }
        List<Sector> sectors = sectorRepository.getAllByVenueId(venueId);
        List<SectorResponse> responses = sectorMapper.toResponseList(sectors);
        return ApiResponse.success(responses);
    }

    @Override
    public ApiResponse<SectorResponse> update(String id, SectorRequest request) {
        log.info("Updating Sector request: {}", request);
        Sector sector = sectorRepository.findById(id).orElseThrow(
                () -> new CustomNotFoundException("Sector not found with id: " + id));
        sector.setName(request.getName());
        sector = sectorRepository.save(sector);
        SectorResponse response = sectorMapper.toResponse(sector);
        return ApiResponse.success(response);
    }

    @Override
    public ApiResponse<Void> delete(String id) {
        log.info("Deleting sector request: {}", id);
        boolean exists = sectorRepository.existsById(id);
        if(!exists){
            throw new CustomNotFoundException("Sector is not exist with id: "+ id);
        }
        sectorRepository.deleteById(id);
        return ApiResponse.success("Deleted successfully");
    }

    private void checkNameIsUnique(SectorRequest request) {
        log.info("Checking for name uniqueness");
        boolean exists = sectorRepository.existsByVenueIdAndName(request.getVenueId(), request.getName().trim());
        if(exists){
            throw new CustomAlreadyExistException("Sector with name "+request.getName().trim()+" already exist");
        }
    }
}
