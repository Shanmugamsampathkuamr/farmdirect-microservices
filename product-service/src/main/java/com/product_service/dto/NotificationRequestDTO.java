package com.product_service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationRequestDTO {
    private Long receiverId;
    private String receiverType;
    private String title;
    private String message;
    private String NotificationType;
    private Long referenceId;
}
