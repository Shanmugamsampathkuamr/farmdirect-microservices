package com.order_service.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequestDTO {

    private Long receiverId;
    private String receiverType;
    private String title;
    private String message;
    private String notificationType;
    private Long referenceId;


}
