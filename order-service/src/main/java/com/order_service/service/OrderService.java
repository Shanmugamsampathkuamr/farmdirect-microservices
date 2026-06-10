package com.order_service.service;

import com.order_service.dto.OrderRequestDTO;
import com.order_service.dto.OrderResponseDTO;
import com.order_service.dto.RunnerResponseDTO;
import com.order_service.model.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OrderService {

    OrderResponseDTO placeOrder(OrderRequestDTO requestDTO);

    OrderResponseDTO getOrderById(Long id);

    List<OrderResponseDTO> getAllOrders();

    List<OrderResponseDTO> getOrdersByUserId(Long userId);

    List<OrderResponseDTO> getAllOrdersByFarmerId(Long farmerId);

    List<OrderResponseDTO> getOrdersByRunnerId(Long runnerId);

    List<OrderResponseDTO> getOrdersByStatus(OrderStatus status);

    OrderResponseDTO updateOrderStatus(Long id ,String status);

    OrderResponseDTO assigningRunner(Long orderId, Long runnerId);


    void cancelOrder(Long id);
}