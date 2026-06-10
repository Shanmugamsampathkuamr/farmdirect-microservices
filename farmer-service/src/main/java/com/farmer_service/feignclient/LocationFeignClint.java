package com.farmer_service.feignclient;

import com.farmer_service.dto.LocationRequestDTO;
import com.farmer_service.dto.LocationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "location-service")
public interface LocationFeignClint {

    @PostMapping("/api/locations/addLocation")
    LocationResponseDTO saveLocation(@RequestBody LocationRequestDTO requestDTO);

}
