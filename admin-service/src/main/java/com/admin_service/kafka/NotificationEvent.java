package com.admin_service.kafka;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationEvent {
    private Long receiverId;
    private String receiverType;
    private String title;
    private String message;
    private String notificationType;
    private Long referenceId;
}
