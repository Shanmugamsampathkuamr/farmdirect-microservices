package com.runner_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "runners")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Runner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false , unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false , unique = true)
    private String phone;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String district;

    private String vehicleType;

    private String vehicleNumber;

    private String aadharNumber;

    private String licenseNumber;

    private String aadharDocumentPath;

    private String licenseDocumentPath;

    @Enumerated(EnumType.STRING)
    private RunnerStatus status;

    @Enumerated(EnumType.STRING)
    private RunnerVerificationStatus verificationStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = RunnerStatus.INACTIVE;
        this.verificationStatus = RunnerVerificationStatus.PENDING;
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt=LocalDateTime.now();
    }

}
