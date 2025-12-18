package uz.shaxzod.ticketapp.mapper;

import org.springframework.stereotype.Component;
import uz.shaxzod.ticketapp.models.entity.Order;
import uz.shaxzod.ticketapp.models.responseDto.OrderResponse;

import java.util.List;

@Component
public class OrderMapper {
    public OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userFirstName(order.getUser() != null ? order.getUser().getFirstName() : "")
                .userSecondName(order.getUser() != null ? order.getUser().getLastName() : "")
                .venueName(order.getShow().getVenue().getName())
                .showTime(order.getShow().getStartTime().toString() +" - " + order.getShow().getEndTime().toString())
                .showDay(order.getShow().getShowDay().toString())
                .seatNumber(order.getShowSeats().stream()
                        .map(showSeats -> showSeats.getSeat().getNumber())
                        .toList())
                .seatLabel(order.getShowSeats().stream()
                        .map(seats -> seats.getSeat().getSeatLabel())
                        .toList())
                .build();
    }

    public List<OrderResponse> toResponseList(List<Order> orders) {
        return orders.stream()
                .map(this::toResponse)
                .toList();
    }
}
