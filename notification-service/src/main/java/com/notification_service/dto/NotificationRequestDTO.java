package com.notification_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequestDTO {

    @NotNull(message = "Reciver Id is required")
    private Long receiverId;

    @NotBlank (message = "Reciver Type is required")
    private String receiverType;

    @NotBlank(message =  "Tittle is required")
    private String title;

    @NotBlank(message = "Message is required")
    private String message;

    @NotBlank(message = "Notification type is required")
    private String notificationType;

    private Long referenceId;


}
