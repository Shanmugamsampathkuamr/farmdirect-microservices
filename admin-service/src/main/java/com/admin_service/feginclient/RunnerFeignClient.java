package com.admin_service.feginclient;

import com.admin_service.dto.RunnerResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "runner-service")
public interface RunnerFeignClient {
    @PutMapping("/api/runners/{id}/approve")
    RunnerResponseDTO approveRunner(@PathVariable Long id);

    @PutMapping("/api/runner/{id}/reject")
    RunnerResponseDTO rejectRunner(@PathVariable Long id);

}
