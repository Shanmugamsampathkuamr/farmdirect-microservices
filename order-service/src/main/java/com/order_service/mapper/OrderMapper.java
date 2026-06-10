package com.order_service.mapper;

import com.order_service.dto.OrderRequestDTO;
import com.order_service.dto.OrderResponseDTO;
import com.order_service.dto.ProductResponseDTO;
import com.order_service.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public Order toEntity(OrderRequestDTO dto , ProductResponseDTO product){

        return Order.builder()
                .userId(dto.getUserId())
                .farmerId(product.getFarmerId())
                .productId(dto.getProductId())
                .cropName(product.getCropName())
                .orderQuantityInKg(dto.getOrderedQuantityInKg())
                .deliveryAddress(dto.getDeliveryAddress())
                .city(dto.getCity())
                .district(dto.getDistrict())
                .build();

    }

    public OrderResponseDTO toDTO(Order order){
        return OrderResponseDTO.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .farmerId(order.getFarmerId())
                .productId(order.getProductId())
                .runnerId(order.getRunnerId())
                .cropName(order.getCropName())
                .orderedQuantityInKg(order.getOrderQuantityInKg())
                .deliveryAddress(order.getDeliveryAddress())
                .city(order.getCity())
                .district(order.getDistrict())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

}
