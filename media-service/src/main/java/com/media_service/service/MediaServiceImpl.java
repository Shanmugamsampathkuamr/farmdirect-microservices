package com.media_service.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.media_service.dto.MediaResponseDTO;
import com.media_service.exception.MediaNotFoundException;
import com.media_service.mapper.MediaMapper;
import com.media_service.model.Media;
import com.media_service.model.MediaType;
import com.media_service.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaServiceImpl implements MediaService {


    private final MediaRepository mediaRepository;
    private final MediaMapper mediaMapper;
    private final Cloudinary cloudinary;



    private MediaResponseDTO uploadFile(MultipartFile file , Long referenceId , String referenceType , MediaType mediaType , String resourceType){
        try{
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "resource_type",referenceType,
                            "folder","farmdirect/"+referenceType.toLowerCase()
                    )
            );
            Media media = Media.builder()
                    .referenceId(referenceId)
                    .referenceType(referenceType)
                    .fileName(file.getOriginalFilename())
                    .fileUrl(uploadResult.get("secure_url").toString())
                    .publicId(uploadResult.get("public_id").toString())
                    .mediaType(mediaType)
                    .fileSize(file.getSize())
                    .build();

            log.info("file uploaded successfully for {} id:{}",referenceType , referenceId);
            return mediaMapper.toDTO(mediaRepository.save(media));

        }catch (IOException e){

            log.error("file uploaded failed:{}",e.getMessage());
            throw new RuntimeException("file upload failed:"+e.getMessage());
        }
    }

    @Override
    public MediaResponseDTO uploadPhoto(MultipartFile file, Long referenceId, String referenceType) {
        return uploadFile(file,referenceId,referenceType,MediaType.PHOTO,"image");
    }

    @Override
    public MediaResponseDTO uploadVideo(MultipartFile file, Long referenceId, String referenceType) {
       return uploadFile(file,referenceId,referenceType,MediaType.VIDEO,"video");
    }

    @Override
    public MediaResponseDTO uploadDocument(MultipartFile file, Long referenceId, String referenceType) {
        return uploadFile(file,referenceId,referenceType,MediaType.DOCUMENT,"raw");
    }

    @Override
    public List<MediaResponseDTO> getMediaByReferenceId(Long referenceId) {
        return mediaRepository.findByReferenceId(referenceId)
                .stream()
                .map(mediaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MediaResponseDTO> getMediaByReferenceIdAndType(Long referenceId, String referenceType) {
        return mediaRepository.findByReferenceIdAndReferenceType(referenceId,referenceType)
                .stream()
                .map(mediaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MediaResponseDTO> getPhotosByReferenceId(Long referenceId) {
        return mediaRepository
                .findByReferenceIdAndMediaType(referenceId,MediaType.PHOTO)
                .stream()
                .map(mediaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MediaResponseDTO> getVideosByReferenceId(Long referenceId) {
        return mediaRepository
                .findByReferenceIdAndMediaType(referenceId,MediaType.VIDEO)
                .stream()
                .map(mediaMapper::toDTO)
                .collect(Collectors.toList());

    }

    @Override
    public List<MediaResponseDTO> findByRefIdANdRefTypAndMediaType(Long referenceId, String referenceType, MediaType mediaType) {
        return mediaRepository.findByReferenceIdAndReferenceTypeAndMediaType(referenceId , referenceType , mediaType)
                .stream()
                .map(mediaMapper::toDTO)
                .collect(Collectors.toList());

    }

    @Override
    public void deleteMedia(Long id) {
        Media media = mediaRepository.findById(id)
                .orElseThrow(()->new MediaNotFoundException("media not found with id:"+id));
        try{
            cloudinary.uploader().destroy(
                    media.getPublicId(),
                    ObjectUtils.emptyMap()
            );
            mediaRepository.deleteById(id);
            log.info("media deleted successfully with id:{}",id);
        }catch (IOException e){
            log.error("media delete failed:{}",e.getMessage());
            throw new RuntimeException("media delete failed:"+e.getMessage());
        }

    }


}
