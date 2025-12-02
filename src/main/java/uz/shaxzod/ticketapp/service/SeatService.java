package uz.shaxzod.ticketapp.service;


import uz.shaxzod.ticketapp.models.requestDto.CreateSeatsRequest;
import uz.shaxzod.ticketapp.models.requestDto.SeatRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.SeatResponse;

import java.util.List;

public interface SeatService {

    ApiResponse<List<SeatResponse>> getAllByShowId(String showId);

    ApiResponse<String> createSeat(SeatRequest request);
    ApiResponse<List<String>> createSeatsRow(CreateSeatsRequest request);
    ApiResponse<List<SeatResponse>> getAll(String venueId);
    ApiResponse<SeatResponse> getById(String seatId);
    ApiResponse<SeatResponse> updateSeat(String seatId, SeatRequest request);
    ApiResponse<Boolean> deleteSeat(String seatId);
    ApiResponse<Boolean> deleteRowSeats(String venueId, String rowNumber);
    ApiResponse<Boolean> deleteSeats(String venueId, List<String> seatsId);

//    ApiResponse<Boolean> markSeatsAsReserved(String showId, List<String> seatIds);
//    ApiResponse<Boolean> releaseReservedSeats(String showId, List<String> seatIds);
//    ApiResponse<SeatStatusResponse> getSeatStatus(String showId, String seatId);
//
//    ApiResponse<Boolean> assignCategoryToSeat(String seatId, String categoryId);
//    ApiResponse<Boolean> removeCategoryFromSeat(String seatId);
//    ApiResponse<Boolean> bulkAssignCategory(String showId, String categoryId, List<String> seatIds);
//
//    ApiResponse<Boolean> setSeatPriceForShow(String showId, String seatId, Double price);
//    ApiResponse<Boolean> setCategoryPriceForShow(String showId, String categoryId, Double price);
//
//    ApiResponse<List<SeatResponse>> getSeatsByVenue(String venueId);
//    ApiResponse<List<SeatResponse>> getSeatsByCategory(String showId, String categoryId);
//    ApiResponse<List<SeatResponse>> getSoldSeats(String showId);
//    ApiResponse<List<SeatResponse>> getLockedSeats(String showId);
//
//    ApiResponse<Boolean> existsById(String seatId);
}