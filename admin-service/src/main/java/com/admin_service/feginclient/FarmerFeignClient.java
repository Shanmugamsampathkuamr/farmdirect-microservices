package com.admin_service.feginclient;


import com.admin_service.dto.FarmerResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "Farmer-service")
public interface FarmerFeignClient {
    @PutMapping("/api/farmers/approve/{id}")
    FarmerResponseDTO approveFarmer(@PathVariable Long id);

    @PutMapping("/api/farmers/reject/{id}")
    FarmerResponseDTO rejectFarmer(@PathVariable Long id);
}
