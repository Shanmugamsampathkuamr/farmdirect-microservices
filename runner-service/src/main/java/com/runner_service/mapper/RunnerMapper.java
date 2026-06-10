package com.runner_service.mapper;


import com.runner_service.dto.RunnerRequestDTO;
import com.runner_service.dto.RunnerResponseDTO;
import com.runner_service.model.Runner;
import org.springframework.stereotype.Component;

@Component
public class RunnerMapper {

    public Runner toEntity(RunnerRequestDTO dto) {
        return Runner.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .phone(dto.getPhone())
                .city(dto.getCity())
                .district(dto.getDistrict())
                .vehicleType(dto.getVehicleType())
                .vehicleNumber(dto.getVehicleNumber())
                .aadharNumber(dto.getAadharNumber())
                .licenseNumber(dto.getLicenseNumber())
                .build();
    }

    public RunnerResponseDTO toDTO(Runner runner) {
        return RunnerResponseDTO.builder()
                .id(runner.getId())
                .fullName(runner.getFullName())
                .email(runner.getEmail())
                .phone(runner.getPhone())
                .city(runner.getCity())
                .district(runner.getDistrict())
                .vehicleType(runner.getVehicleType())
                .vehicleNumber(runner.getVehicleNumber())
                .aadharNumber(runner.getAadharNumber())
                .licenseNumber(runner.getLicenseNumber())
                .aadharDocumentPath(runner.getAadharDocumentPath())
                .licenseDocumentPath(runner.getLicenseDocumentPath())
                .status(runner.getStatus())
                .verificationStatus(runner.getVerificationStatus())
                .createdAt(runner.getCreatedAt())
                .build();
    }
}
