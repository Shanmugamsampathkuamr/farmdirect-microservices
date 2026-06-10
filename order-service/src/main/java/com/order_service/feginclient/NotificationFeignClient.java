package com.order_service.feginclient;


import com.order_service.dto.NotificationRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service")
public interface NotificationFeignClient {

    @PostMapping("/api/notifications")
    NotificationRequestDTO sendNotification(@RequestBody NotificationRequestDTO requestDTO);
}
