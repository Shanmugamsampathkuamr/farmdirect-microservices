package com.notification_service.service;

import com.notification_service.dto.NotificationRequestDTO;
import com.notification_service.dto.NotificationResponseDTO;

import java.util.List;

public interface NotificationService {

    NotificationResponseDTO sendNotification(NotificationRequestDTO responseDTO);

    NotificationResponseDTO getNotificationById(Long id);

    List<NotificationResponseDTO> getNotificationsByReceiverId(Long receiverId);

    List<NotificationResponseDTO> getUnreadNotifications(Long receiverId , String receiverType);

    List<NotificationResponseDTO> getAllNotificationsByReceiverIdAndType(Long receiverId , String receiverType);

    NotificationResponseDTO markAsRead(Long id);

    void markAllAsRead(Long receiverId , String receiverType);

    long getUnreadCount(Long receiverId);

    void deleteNotification(Long id);

}
