package com.notification_service.repository;

import com.notification_service.model.Notification;
import com.notification_service.model.NotificationStatus;
import com.notification_service.model.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification , Long> {

    List<Notification> findByReceiverId(Long receiverId);

    List<Notification> findByReceiverIdAndReceiverType(Long receiverId , String receiverType);

    List<Notification> findByReceiverIdAndStatus(Long receiverId , NotificationStatus status);

    List<Notification> findByReceiverIdAndReceiverTypeAndStatus(Long receiverId , String receiverType , NotificationStatus status);

    List<Notification> findByNotificationType(NotificationType notificationType);

    long countByReceiverIdAndStatus(Long receiverId , NotificationStatus status);

}
