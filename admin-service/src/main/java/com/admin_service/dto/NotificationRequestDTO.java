package com.admin_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequestDTO {
    private Long receiverId;
    private String receiverType;
    private String tittle;
    private String message;
    private String notificationType;
    private Long referenceId;
}
