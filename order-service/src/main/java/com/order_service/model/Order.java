package com.order_service.model;

import jakarta.persistence.*;
import jakarta.ws.rs.Encoded;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long farmerId;

    @Column(nullable = false)
    private Long productId;

    private Long runnerId;

    @Column(nullable = false)
    private String cropName;

    @Column(nullable = false)
    private Double orderQuantityInKg;

    @Column(nullable = false)
    private String deliveryAddress;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String district;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = OrderStatus.PLACED;
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }


}
