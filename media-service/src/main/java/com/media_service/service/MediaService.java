package com.media_service.service;

import com.media_service.dto.MediaResponseDTO;
import com.media_service.model.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {

    MediaResponseDTO uploadPhoto(MultipartFile file , Long referenceId , String referenceType);

    MediaResponseDTO uploadVideo(MultipartFile file , Long  referenceId , String referenceType );

    MediaResponseDTO uploadDocument(MultipartFile file , Long  referenceId , String referenceType );

    List<MediaResponseDTO> getMediaByReferenceId(Long referenceId);
    List<MediaResponseDTO> getMediaByReferenceIdAndType(Long referenceId , String referenceType);
    List<MediaResponseDTO> getPhotosByReferenceId(Long referenceId);
    List<MediaResponseDTO> getVideosByReferenceId(Long referenceId);

    List<MediaResponseDTO> findByRefIdANdRefTypAndMediaType(Long referenceId , String referenceType , MediaType mediaType);

    void deleteMedia(Long id);

}
