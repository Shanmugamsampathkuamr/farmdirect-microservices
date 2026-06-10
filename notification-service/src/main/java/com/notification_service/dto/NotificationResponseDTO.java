package com.notification_service.dto;

import com.notification_service.model.NotificationStatus;
import com.notification_service.model.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponseDTO {
    private Long id;
    private Long receiverId;
    private String receiverType;
    private String title;
    private String message;
    private NotificationType notificationType;
    private NotificationStatus status;
    private Long referenceId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
