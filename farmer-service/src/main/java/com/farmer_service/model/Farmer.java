package com.farmer_service.model;


import jakarta.persistence.*;
import lombok.*;

import java.awt.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "farmer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Farmer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false )
    private String fullName;

    @Column(nullable = false , unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false , unique = true)
    private String phone;

    @Column(nullable = false)
    private String village;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String state;

    private String farmSizeInAcer;

    private String aadharNumber;

    private String aadharDocumentPath;

    private String farmPhotoPath;

    @Enumerated(EnumType.STRING)
    private FarmerStatus status;

    @Enumerated(EnumType.STRING)
    private FarmerVerificationStatus verificationStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = FarmerStatus.INACTIVE;
        this.verificationStatus = FarmerVerificationStatus.PENDING;
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }





}
