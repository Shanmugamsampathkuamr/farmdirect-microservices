package com.farmer_service.dto;

import com.farmer_service.model.FarmerStatus;
import com.farmer_service.model.FarmerVerificationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FarmerResponseDTo {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String village;
    private String city;
    private String district;
    private String state;
    private String farmSizeInAcres;
    private String aadharNumber;
    private String aadharDocumentPath;
    private String farmPhotoPath;
    private FarmerStatus status;
    private FarmerVerificationStatus verificationStatus;
    private LocalDateTime cratedAt;


}
