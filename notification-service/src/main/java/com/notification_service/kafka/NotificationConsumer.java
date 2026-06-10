package com.notification_service.kafka;

import com.notification_service.dto.NotificationRequestDTO;
import com.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "order-notification",
            groupId = "notification-group"
    )
    public void consumeOrderNotification(NotificationEvent event) {
        log.info("Received order notification for: {}",
                event.getReceiverId());
        saveNotification(event);
    }

    @KafkaListener(
            topics = "product-notification",
            groupId = "notification-group"
    )
    public void consumeProductNotification(NotificationEvent event) {
        log.info("Received product notification for: {}",
                event.getReceiverId());
        saveNotification(event);
    }

    @KafkaListener(
            topics = "admin-notification",
            groupId = "notification-group"
    )
    public void consumeAdminNotification(NotificationEvent event) {
        log.info("Received admin notification for: {}",
                event.getReceiverId());
        saveNotification(event);
    }

    private void saveNotification(NotificationEvent event) {
        try {
            notificationService.sendNotification(
                    NotificationRequestDTO.builder()
                            .receiverId(event.getReceiverId())
                            .receiverType(event.getReceiverType())
                            .title(event.getTitle())
                            .message(event.getMessage())
                            .notificationType(event.getNotificationType())
                            .referenceId(event.getReferenceId())
                            .build()
            );
            log.info("Notification saved for: {}",
                    event.getReceiverId());
        } catch (Exception e) {
            log.error("Failed to save notification: {}",
                    e.getMessage());
        }
    }
}