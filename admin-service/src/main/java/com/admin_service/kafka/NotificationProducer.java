package com.admin_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {

    private final KafkaTemplate<String , NotificationEvent> kafkaTemplate;

    private static final String TOPIC = "admin-notification";

    public void sendNotification(NotificationEvent event) {
        try {
            kafkaTemplate.send(TOPIC, event);
            log.info("Sent to topic: {} for receiver: {}",
                    TOPIC, event.getReceiverId());
        } catch (Exception e) {
            log.error("Failed to send: {}", e.getMessage());
        }
    }
}
