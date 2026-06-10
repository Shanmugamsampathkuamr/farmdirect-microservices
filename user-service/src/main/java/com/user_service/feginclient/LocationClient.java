package com.user_service.feginclient;

import com.user_service.dto.LocationRequestDTO;
import com.user_service.dto.LocationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "location-service")
public interface LocationClient {

    @PostMapping("/api/locations/addLocation")
    LocationResponseDTO saveLocation(@RequestBody LocationRequestDTO requestDTO);
}
