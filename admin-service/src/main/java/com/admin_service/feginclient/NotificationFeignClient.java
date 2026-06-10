package com.admin_service.feginclient;


import com.admin_service.dto.NotificationRequestDTO;
import com.admin_service.dto.NotificationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service")
public interface NotificationFeignClient {
    @PostMapping("/api/notifications")
    NotificationResponseDTO sendNotification(@RequestBody NotificationRequestDTO requestDTO);
}
