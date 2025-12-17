package uz.shaxzod.ticketapp.service;

import uz.shaxzod.ticketapp.models.requestDto.OrderRequest;
import uz.shaxzod.ticketapp.models.requestDto.PaymentCallbackRequest;
import uz.shaxzod.ticketapp.models.responseDto.ApiResponse;
import uz.shaxzod.ticketapp.models.responseDto.OrderResponse;

import java.util.List;

public interface OrderService {
    ApiResponse<String> create(OrderRequest request, String paymentMethod);
    ApiResponse<OrderResponse> get(String id);
    ApiResponse<Void> cancel(String id);
    ApiResponse<OrderResponse> getMyOrders();
    ApiResponse<String> paymentCallback(PaymentCallbackRequest request); // Payment providers notify my system.
    //for admins
    ApiResponse<List<OrderResponse>> getAllByShowId(String showId);
    ApiResponse<Void> refund(String orderId);
}
