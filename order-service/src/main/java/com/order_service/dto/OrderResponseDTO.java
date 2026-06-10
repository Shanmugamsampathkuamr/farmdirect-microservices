package com.order_service.dto;

import com.order_service.model.OrderStatus;
import lombok.*;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long id;
    private Long userId;
    private Long farmerId;
    private Long productId;
    private Long runnerId;
    private String cropName;
    private Double orderedQuantityInKg;
    private String deliveryAddress;
    private String city;
    private String district;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
