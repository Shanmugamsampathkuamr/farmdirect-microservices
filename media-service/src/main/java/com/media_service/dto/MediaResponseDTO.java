package com.media_service.dto;

import com.media_service.model.MediaType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaResponseDTO {
    private Long id;
    private Long referenceId;
    private String referenceType;
    private String fileName;
    private String fileUrl;
    private String publicId;
    private MediaType mediaType;
    private Long fileSize;
    private LocalDateTime createdAt;

}
