package com.notification_service.service;

import com.notification_service.dto.NotificationRequestDTO;
import com.notification_service.dto.NotificationResponseDTO;
import com.notification_service.exception.NotificationNotFoundException;
import com.notification_service.mapper.NotificationMapper;
import com.notification_service.model.Notification;
import com.notification_service.model.NotificationStatus;
import com.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;



    @Override
    public NotificationResponseDTO sendNotification(NotificationRequestDTO responseDTO) {
        Notification notification = notificationMapper.toEntity(responseDTO);
        Notification saved = notificationRepository.save(notification);
        log.info("Notification sent to{} with id:{}",
                responseDTO.getReceiverType(),responseDTO.getReceiverId() );
        return notificationMapper.toDTO(saved);
    }

    @Override
    public NotificationResponseDTO getNotificationById(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(
                        "Notification not found with id: " + id));
        return notificationMapper.toDTO(notification);
    }

    @Override
    public List<NotificationResponseDTO> getNotificationsByReceiverId(Long receiverId) {
        return notificationRepository.findByReceiverId(receiverId)
                .stream()
                .map(notificationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponseDTO> getUnreadNotifications(
            Long receiverId, String receiverType) {
        return notificationRepository
                .findByReceiverIdAndReceiverTypeAndStatus(
                        receiverId, receiverType, NotificationStatus.UNREAD)
                .stream()
                .map(notificationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponseDTO> getAllNotificationsByReceiverIdAndType(
            Long receiverId, String receiverType) {
        return notificationRepository
                .findByReceiverIdAndReceiverType(receiverId, receiverType)
                .stream()
                .map(notificationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationResponseDTO markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(
                        "Notification not found with id: " + id));
        notification.setStatus(NotificationStatus.READ);
        return notificationMapper.toDTO(notificationRepository.save(notification));
    }

    @Override
    public void markAllAsRead(Long receiverId, String receiverType) {
        List<Notification> unreadList = notificationRepository
                .findByReceiverIdAndReceiverTypeAndStatus(
                        receiverId, receiverType, NotificationStatus.UNREAD);
        unreadList.forEach(n -> n.setStatus(NotificationStatus.READ));
        notificationRepository.saveAll(unreadList);
        log.info("Marked all notifications as read for {} id: {}",
                receiverType, receiverId);
    }

    @Override
    public long getUnreadCount(Long receiverId) {
        return notificationRepository.countByReceiverIdAndStatus(
                receiverId, NotificationStatus.UNREAD);
    }

    @Override
    public void deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new NotificationNotFoundException(
                    "Notification not found with id: " + id);
        }
        notificationRepository.deleteById(id);
    }
}
