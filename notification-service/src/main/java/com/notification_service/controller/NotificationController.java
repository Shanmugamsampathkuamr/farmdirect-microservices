package com.notification_service.controller;


import com.notification_service.dto.NotificationRequestDTO;
import com.notification_service.dto.NotificationResponseDTO;
import com.notification_service.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponseDTO> sendNotification(
            @Valid @RequestBody NotificationRequestDTO requestDTO) {
        return new ResponseEntity<>(
                notificationService.sendNotification(requestDTO),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponseDTO> getNotificationById(
            @PathVariable Long id) {
        return ResponseEntity.ok(notificationService.getNotificationById(id));
    }

    // GET /api/notifications/receiver/1
    @GetMapping("/receiver/{receiverId}")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByReceiverId(
            @PathVariable Long receiverId) {
        return ResponseEntity.ok(
                notificationService.getNotificationsByReceiverId(receiverId));
    }

    // GET /api/notifications/unread?receiverId=1&receiverType=FARMER
    @GetMapping("/unread")
    public ResponseEntity<List<NotificationResponseDTO>> getUnreadNotifications(
            @RequestParam Long receiverId,
            @RequestParam String receiverType) {
        return ResponseEntity.ok(
                notificationService.getUnreadNotifications(receiverId, receiverType));
    }

    // GET /api/notifications/all?receiverId=1&receiverType=USER
    @GetMapping("/all")
    public ResponseEntity<List<NotificationResponseDTO>> getAllNotificationsByReceiverIdAndType(
            @RequestParam Long receiverId,
            @RequestParam String receiverType) {
        return ResponseEntity.ok(notificationService
                .getAllNotificationsByReceiverIdAndType(receiverId, receiverType));
    }

    // GET /api/notifications/unread/count?receiverId=1
    @GetMapping("/unread/count")
    public ResponseEntity<Long> getUnreadCount(@RequestParam Long receiverId) {
        return ResponseEntity.ok(notificationService.getUnreadCount(receiverId));
    }

    // PUT /api/notifications/1/read
    @PutMapping("/{id}/read")
    public ResponseEntity<NotificationResponseDTO> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }

    // PUT /api/notifications/read-all?receiverId=1&receiverType=FARMER
    @PutMapping("/read-all")
    public ResponseEntity<String> markAllAsRead(
            @RequestParam Long receiverId,
            @RequestParam String receiverType) {
        notificationService.markAllAsRead(receiverId, receiverType);
        return ResponseEntity.ok("All notifications marked as read");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok("Notification deleted successfully");
    }
}
