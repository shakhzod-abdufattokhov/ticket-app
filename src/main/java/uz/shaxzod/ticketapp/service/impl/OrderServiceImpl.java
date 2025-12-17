package uz.shaxzod.ticketapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.shaxzod.ticketapp.exceptions.CustomInternalErrorException;
import uz.shaxzod.ticketapp.exceptions.CustomNotFoundException;
import uz.shaxzod.ticketapp.models.entity.*;
import uz.shaxzod.ticketapp.models.enums.OrderStatus;
import uz.shaxzod.ticketapp.models.redis.Reservation;
import uz.shaxzod.ticketapp.models.requestDto.OrderRequest;
import uz.shaxzod.ticketapp.models.requestDto.PaymentCallbackRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.OrderResponse;
import uz.shaxzod.ticketapp.repository.*;
import uz.shaxzod.ticketapp.repository.redis.ReservationRepository;
import uz.shaxzod.ticketapp.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final ShowRepository showRepository;
    private final ShowSeatsRepository showSeatsRepository;

    @Override
    public ApiResponse<String> create(OrderRequest request, String paymentMethod) {
        log.info("Create Order request: {}, payment-method: {}", request, paymentMethod);

        List<Reservation> reservations = new ArrayList<>();
        for(String id : request.getReservationId()){
            Optional<Reservation> reservation = reservationRepository.findById(id);
            if(reservation.isEmpty()){
                return ApiResponse.error("Reservation expired: "+id);
            }
            reservations.add(reservation.get());
        }

        Order order = createOrder(reservations);

        Order savedOrder = orderRepository.save(order);
        return ApiResponse.success(savedOrder.getId(), "Order created successfully");
    }

    private Order createOrder(List<Reservation> reservations) {
        User user = userRepository.findById(reservations.get(0).getUserId()).orElseThrow(
                () -> new CustomNotFoundException("User not found with id: "+ reservations.get(0).getUserId()));

        Show show = showRepository.findById(reservations.get(0).getShowId()).orElseThrow(
                () -> new CustomNotFoundException("Show not found with id: "+ reservations.get(0).getShowId()));

        List<ShowSeats> showSeats = new ArrayList<>();
        Long totalAmount = 0L;
        for(Reservation reservation : reservations){
            List<ShowSeats> showSeat = showSeatsRepository.findByShowIdAndSeatId(reservation.getShowId(), reservation.getSeatId());
            if(showSeat.size() != 1){
                throw new CustomInternalErrorException("CreateOrder request: Fetching ShowSeats with showId and seatId returning multiple showSeats");}
            showSeats.addAll(showSeat);
            totalAmount += showSeat.get(0).getPrice();
        }
        log.info("All showSeats: {}", showSeats);


        return Order.builder()
                .user(user)
                .show(show)
                .showSeats(showSeats)
                .totalAmount(totalAmount)
                .status(OrderStatus.PENDING)
                .build();
    }

    @Override
    public ApiResponse<OrderResponse> get(String id) {
        return null;
    }

    @Override
    public ApiResponse<Void> cancel(String id) {
        return null;
    }

    @Override
    public ApiResponse<OrderResponse> getMyOrders() {
        return null;
    }

    @Override
    public ApiResponse<String> paymentCallback(PaymentCallbackRequest request) {
        return null;
    }

    @Override
    public ApiResponse<List<OrderResponse>> getAllByShowId(String showId) {
        return null;
    }

    @Override
    public ApiResponse<Void> refund(String orderId) {
        return null;
    }
}
