package com.farmer_service.mapper;

import com.farmer_service.dto.FarmerResponseDTo;
import com.farmer_service.dto.FarmerRequestDTO;
import com.farmer_service.model.Farmer;
import org.springframework.stereotype.Component;

@Component
public class FarmerMapper {

    public Farmer toEntity(FarmerRequestDTO dto){
        return Farmer.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .phone(dto.getPhone())
                .village(dto.getVillage())
                .city(dto.getCity())
                .district(dto.getDistrict())
                .state(dto.getState())
                .farmSizeInAcer(dto.getFarmSizeInAcres())
                .aadharNumber(dto.getAadharNumber())
                .build();

    }

    public FarmerResponseDTo toDTO(Farmer farmer){
        return FarmerResponseDTo.builder()
                .id(farmer.getId())
                .fullName(farmer.getFullName())
                .email(farmer.getEmail())
                .phone(farmer.getPhone())
                .village(farmer.getVillage())
                .city(farmer.getCity())
                .district(farmer.getDistrict())
                .state(farmer.getState())
                .farmSizeInAcres(farmer.getFarmSizeInAcer())
                .aadharNumber(farmer.getAadharNumber())
                .aadharDocumentPath(farmer.getAadharDocumentPath())
                .farmPhotoPath(farmer.getFarmPhotoPath())
                .status(farmer.getStatus())
                .verificationStatus(farmer.getVerificationStatus())
                .cratedAt(farmer.getCreatedAt())
                .build();
    }

}
