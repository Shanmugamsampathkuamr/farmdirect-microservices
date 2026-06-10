package com.order_service.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDTO {

    private Long id;
    private Long farmerId;
    private String cropName;
    private String description;
    private Double quantityInKg;
    private String city;
    private String district;
    private String village;
    private String photoPath;
    private String videoPath;
    private LocalDate harvestDate;
    private Integer storableDate;
    private LocalDate expiryDate;
    private String status;
    private LocalDateTime createdAt;
}
