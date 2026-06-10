package com.media_service.mapper;

import com.media_service.dto.MediaResponseDTO;
import com.media_service.model.Media;
import org.springframework.stereotype.Component;

@Component
public class MediaMapper {

    public MediaResponseDTO toDTO(Media media){
        return MediaResponseDTO.builder()
                .id(media.getId())
                .referenceId(media.getReferenceId())
                .referenceType(media.getReferenceType())
                .fileName(media.getFileName())
                .fileUrl(media.getFileUrl())
                .publicId(media.getPublicId())
                .mediaType(media.getMediaType())
                .fileSize(media.getFileSize())
                .createdAt(media.getCreatedAt())
                .build();
    }
}
