package com.product_service.feignclient;

import com.product_service.dto.NotificationRequestDTO;
import com.product_service.dto.NotificationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service")
public interface NotificationFeignClient {
    @PostMapping("/api/notifications")
    NotificationResponseDTO sendNotifications(@RequestBody NotificationRequestDTO requestDTO);
}
