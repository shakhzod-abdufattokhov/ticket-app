package uz.shaxzod.ticketapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.shaxzod.ticketapp.exceptions.CustomAlreadyExistException;
import uz.shaxzod.ticketapp.exceptions.CustomBadRequestException;
import uz.shaxzod.ticketapp.exceptions.CustomNotFoundException;
import uz.shaxzod.ticketapp.mapper.SeatMapper;
import uz.shaxzod.ticketapp.mapper.ShowMapper;
import uz.shaxzod.ticketapp.models.entity.Seat;
import uz.shaxzod.ticketapp.models.entity.Venue;
import uz.shaxzod.ticketapp.models.enums.SeatType;
import uz.shaxzod.ticketapp.models.requestDto.CreateSeatsRequest;
import uz.shaxzod.ticketapp.models.requestDto.SeatDeleteRequest;
import uz.shaxzod.ticketapp.models.requestDto.SeatRequest;
import uz.shaxzod.ticketapp.models.requestDto.SeatUpdateRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.SeatResponse;
import uz.shaxzod.ticketapp.repository.SeatRepository;
import uz.shaxzod.ticketapp.repository.VenueRepository;
import uz.shaxzod.ticketapp.service.SeatService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;
    private final VenueRepository venueRepository;
    private final ShowMapper showMapper;

    @Override
    public ApiResponse<List<SeatResponse>> getAllByShowId(String showId) {

        return null;
    }

    @Override
    public ApiResponse<String> createSeat(SeatRequest request) {
        log.info("Create seat request: "+ request.toString());
        Venue venue = venueRepository.findById(request.getVenueId()).orElseThrow(
                () -> new CustomNotFoundException("Venue not found with id: " + request.getVenueId()));

        checkIsSeatExist(venue.getSeats(), request);
        Seat seat = seatMapper.toEntity(request);
        seat.setVenue(venue);

        Seat save = seatRepository.save(seat);
        return ApiResponse.success(save.getId(), "Seat created successfully");
    }


    @Override
    public ApiResponse<List<String>> createSeatsRow(CreateSeatsRequest request) {
        log.info("Creating Batch seats with row, section and number of seats: {}", request.toString());
        Venue venue = venueRepository.findById(request.getVenueId()).orElseThrow(
                () -> new CustomNotFoundException("Venue not found with id: " + request.getVenueId()));

        checkIsRowExist(request);
        List<Seat> seats = makeSeats(request, venue);
        List<Seat> savedSeats = seatRepository.saveAll(seats);

        List<String> ids = savedSeats.stream()
                .map(Seat::getId)
                .toList();
        return ApiResponse.success(ids);
    }


    @Override
    public ApiResponse<List<SeatResponse>> getAll(String venueId) {
        log.info("Get all seats by venueId {}", venueId);
        venueRepository.findById(venueId).orElseThrow(
                () -> new CustomNotFoundException("Venue not found with id: " + venueId));

        List<Seat> seats = seatRepository.findByVenueId(venueId);
        if(seats.isEmpty()){
            return ApiResponse.success(Collections.emptyList());
        }
        List<SeatResponse> seatResponses = seatMapper.toResponseList(seats);
        return ApiResponse.success(seatResponses);
    }

    @Override
    public ApiResponse<SeatResponse> getById(String seatId) {
        log.info("Get Seat by id: {}", seatId);
        Seat seat = seatRepository.findById(seatId).orElseThrow(
                () -> new CustomNotFoundException("Seat not found with id: " + seatId));
        SeatResponse seatResponse = seatMapper.toResponse(seat);
        return ApiResponse.success(seatResponse);
    }

    @Override
    public ApiResponse<SeatResponse> updateSeat(String seatId, SeatUpdateRequest request) {
        log.info("Update seat with id {}, by request {}", seatId, request.toString());
        Seat seat = seatRepository.findById(seatId).orElseThrow(
                () -> new CustomNotFoundException("Seat not found with id: " + seatId));
        seat = seatMapper.toEntityUpdate(seat, request);
        seat = seatRepository.save(seat);

        SeatResponse response = seatMapper.toResponse(seat);
        return ApiResponse.success(response);
    }

    @Override
    public ApiResponse<Void> deleteSeat(String seatId) {
        log.info("Delete seat by id {}", seatId);
        seatRepository.findById(seatId).orElseThrow(
                () -> new CustomNotFoundException("Seat not found with id: " + seatId));

        seatRepository.deleteById(seatId);
        return ApiResponse.success("Deleted successfully");
    }

    @Override
    public ApiResponse<Void> deleteRowSeats(String venueId, Integer rowNumber) {
        log.info("Delete seats with row {} of venue {} request", rowNumber, venueId);
        List<Seat> seats = seatRepository.findByVenueId(venueId);
        boolean exists = seats.stream()
                .anyMatch(seat -> Objects.equals(seat.getRow(), rowNumber));
        if(!exists){
            throw new CustomBadRequestException("There is not row with order number: "+ rowNumber);
        }
        seatRepository.deleteAllByVenueIdAndRow(venueId, rowNumber);
        return ApiResponse.success("Deleted successfully");
    }

    @Override
    public ApiResponse<Void> deleteSeats(SeatDeleteRequest request) {
        log.info("Delete seats request");
        for(String id : request.getSeatIds()){
            boolean exists = seatRepository.existsById(id);
            if(exists){
                seatRepository.deleteById(id);
            }
        }
        return ApiResponse.success("Delete successfully");
    }

    private void checkIsSeatExist(List<Seat> seats, SeatRequest request) {
        boolean isExist =  seats.stream()
                .anyMatch(seat -> Objects.equals(seat.getRow(), request.getRow()) &&
                        Objects.equals(seat.getNumber(), request.getNumber()) &&
                        Objects.equals(seat.getSection(), request.getSection()));

        if(isExist){
            throw new CustomAlreadyExistException(
                    String.format("Seat is exist with row %d and number %d", request.getRow(), request.getNumber()));
        }
    }

    private void checkIsRowExist(CreateSeatsRequest request) {
        boolean exists = seatRepository.existsByVenueIdAndSectionAndRow(request.getVenueId(), request.getSection(), request.getRow());
        if(exists){
            throw new CustomAlreadyExistException("This row is already exist, for recreating it with seats, please delete it!");
        }
    }

    private List<Seat> makeSeats(CreateSeatsRequest request, Venue venue) {
        if(request.getNumOfSeats() < 1){
            return Collections.emptyList();
        }

        List<Seat> seats = new ArrayList<>();
        for(int i = 1; i<=request.getNumOfSeats(); i++){
            seats.add(
                    Seat.builder()
                            .venue(venue)
                            .section(request.getSection())
                            .row(request.getRow())
                            .number(i)
                            .type(SeatType.REGULAR) // default type is regular
                            .build()
            );
        }
        return seats;
    }
}
