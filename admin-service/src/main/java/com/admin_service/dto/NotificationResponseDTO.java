package com.admin_service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponseDTO {
    private Long id;
    private String title;
    private String message;
}
