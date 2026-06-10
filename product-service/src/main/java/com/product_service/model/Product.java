package com.product_service.model;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long farmerId;

    @Column(nullable = false)
    private String cropName;

    private String description;

    @Column(nullable = false)
    private Double quantityInKg;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String district;

    private String village;

    private String photoPath;

    private String videoPath;

    @Column(nullable = false)
    private LocalDate harvestDate;

    @Column(nullable = false)
    private Integer storableDays;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @Column(nullable = false)
    private ProductStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = ProductStatus.AVAILABLE;
        
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }




}
