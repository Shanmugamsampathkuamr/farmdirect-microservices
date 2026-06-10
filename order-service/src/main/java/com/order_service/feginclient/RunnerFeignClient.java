package com.order_service.feginclient;


import com.order_service.dto.RunnerResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "runner-service")
public interface RunnerFeignClient {
    @PutMapping("/api/runners/{id}/status")
    RunnerResponseDTO updateRunnerStatus(@PathVariable Long id , @RequestParam String status);
}
