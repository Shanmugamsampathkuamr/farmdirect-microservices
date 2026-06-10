package com.runner_service.feignclient;

import com.runner_service.dto.LocationRequestDTO;
import com.runner_service.dto.LocationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "location-service")
public interface LocationFeignClient {

    @PostMapping("/api/locations/addLocation")
    LocationResponseDTO saveLocation(@RequestBody LocationRequestDTO requestDTO);

}
