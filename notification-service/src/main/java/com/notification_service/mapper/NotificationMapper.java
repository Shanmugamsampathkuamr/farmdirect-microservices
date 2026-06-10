package com.notification_service.mapper;

import com.notification_service.dto.NotificationRequestDTO;
import com.notification_service.dto.NotificationResponseDTO;
import com.notification_service.model.Notification;
import com.notification_service.model.NotificationType;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public Notification toEntity(NotificationRequestDTO dto){
        return Notification.builder()
                .receiverId(dto.getReceiverId())
                .receiverType(dto.getReceiverType())
                .title(dto.getTitle())
                .message(dto.getMessage())
                .notificationType(NotificationType.valueOf(dto.getNotificationType()))
                .referenceId(dto.getReferenceId())
                .build();
    }

    public NotificationResponseDTO toDTO(Notification notification){
        return NotificationResponseDTO.builder()
                .id(notification.getId())
                .receiverId(notification.getReceiverId())
                .receiverType(notification.getReceiverType())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .notificationType(notification.getNotificationType())
                .status(notification.getStatus())
                .referenceId(notification.getReferenceId())
                .createdAt(notification.getCreatedAt())
                .updatedAt(notification.getUpdatedAt())
                .build();
    }

}
