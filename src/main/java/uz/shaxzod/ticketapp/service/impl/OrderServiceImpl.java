package uz.shaxzod.ticketapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.shaxzod.ticketapp.exceptions.CustomBadRequestException;
import uz.shaxzod.ticketapp.exceptions.CustomInternalErrorException;
import uz.shaxzod.ticketapp.exceptions.CustomNotFoundException;
import uz.shaxzod.ticketapp.mapper.OrderMapper;
import uz.shaxzod.ticketapp.models.entity.Order;
import uz.shaxzod.ticketapp.models.entity.Show;
import uz.shaxzod.ticketapp.models.entity.ShowSeats;
import uz.shaxzod.ticketapp.models.entity.User;
import uz.shaxzod.ticketapp.models.enums.OrderStatus;
import uz.shaxzod.ticketapp.models.redis.Reservation;
import uz.shaxzod.ticketapp.models.requestDto.OrderRequest;
import uz.shaxzod.ticketapp.models.requestDto.PaymentCallbackRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.OrderResponse;
import uz.shaxzod.ticketapp.repository.OrderRepository;
import uz.shaxzod.ticketapp.repository.ShowRepository;
import uz.shaxzod.ticketapp.repository.ShowSeatsRepository;
import uz.shaxzod.ticketapp.repository.UserRepository;
import uz.shaxzod.ticketapp.repository.redis.ReservationRepository;
import uz.shaxzod.ticketapp.service.OrderService;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final ShowRepository showRepository;
    private final ShowSeatsRepository showSeatsRepository;
    private final OrderMapper orderMapper;

    @Override
    public ApiResponse<String> create(OrderRequest request, String paymentMethod) {
        log.info("Create Order request: {}, payment-method: {}", request, paymentMethod);
        List<String> reservationIds = request.getReservationId();
        List<Reservation> reservations = (List<Reservation>) reservationRepository.findAllById(reservationIds);
        if (reservations.size() != reservationIds.size()) {
            throw new CustomBadRequestException("Some reservations are expired or not found");
        }

        Order order = createOrder(reservations);
        Order savedOrder = orderRepository.save(order);

        return ApiResponse.success(savedOrder.getId(), "Order created successfully");
    }

    @Override
    public ApiResponse<OrderResponse> get(String id) {
        log.info("Get Order request: {}",id);
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new CustomNotFoundException("Order not found with id: "+ id));
        OrderResponse response = orderMapper.toResponse(order);
        return ApiResponse.success(response);
    }

    @Override
    public ApiResponse<Void> cancel(String id) {
        log.info("Cancel order request: {}", id);
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new CustomNotFoundException("Order not found with id: "+ id));
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        return ApiResponse.success("Order canceled");
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
        log.info("Get all orders by show-id request: {}", showId);
        List<Order> orders = orderRepository.getAllByShowId(showId);
        if(orders.isEmpty()){
            return ApiResponse.success(Collections.emptyList());
        }
        List<OrderResponse> responseList = orderMapper.toResponseList(orders);

        return ApiResponse.success(responseList);
    }

    @Override
    public ApiResponse<Void> refund(String orderId) {
        return null;
    }


    private Order createOrder(List<Reservation> reservations) {
        String showId = reservations.get(0).getShowId();
        String userId = reservations.get(0).getUserId();
        boolean sameShow = reservations.stream()
                .allMatch(r -> r.getShowId().equals(showId) && r.getUserId().equals(userId));

        if (!sameShow) {
            throw new CustomBadRequestException("All reservations must belong to the same show");
        }

/*        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomNotFoundException("User not found wiht id: "+ userId));*/
        User user = null; // TODO: get from security context or reservation
        Show show = showRepository.findById(showId).orElseThrow(
                () -> new CustomNotFoundException("Show not found with id: " + showId));

        List<String> seatIds = reservations.stream()
                .map(Reservation::getSeatId)
                .toList();

        List<ShowSeats> showSeats = showSeatsRepository.findByShowIdAndSeatIdIn(showId, seatIds);
        if (showSeats.size() != reservations.size()) {
            throw new CustomInternalErrorException("Mismatch between reservations and show seats");}

        long totalAmount = showSeats.stream()
                .mapToLong(ShowSeats::getPrice)
                .sum();

        return Order.builder()
                .user(user)
                .show(show)
                .showSeats(showSeats)
                .totalAmount(totalAmount)
                .status(OrderStatus.PENDING)
                .build();
    }

}
