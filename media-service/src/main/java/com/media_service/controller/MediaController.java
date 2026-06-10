package com.media_service.controller;

import com.media_service.dto.MediaResponseDTO;
import com.media_service.model.Media;
import com.media_service.model.MediaType;
import com.media_service.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class MediaController {


    private final MediaService mediaService;

    @PostMapping("/upload/photo")
    public ResponseEntity<MediaResponseDTO> uploadPhoto(@RequestParam MultipartFile file , @RequestParam Long referenceId , @RequestParam String referenceTyp){
        return new ResponseEntity<>(mediaService.uploadPhoto(file,referenceId,referenceTyp), HttpStatus.CREATED);
    }

    @PostMapping("/upload/video")
    public ResponseEntity<MediaResponseDTO> uploadVideo(@RequestParam MultipartFile file , @RequestParam Long referenceId , @RequestParam String referenceType){
        return new ResponseEntity<>(mediaService.uploadVideo(file,referenceId,referenceType),HttpStatus.CREATED);
    }

    @PostMapping("/upload/document")
    public ResponseEntity<MediaResponseDTO> uploadDocument(@RequestParam MultipartFile file , @RequestParam Long referenceId , @RequestParam String referenceType){
        return new ResponseEntity<>(mediaService.uploadDocument(file,referenceId,referenceType),HttpStatus.CREATED);
    }

    @GetMapping("/reference/{referenceId}")
    public ResponseEntity<List<MediaResponseDTO>> getMediaByReferenceId(@PathVariable Long referenceId){
        return ResponseEntity.ok(mediaService.getMediaByReferenceId(referenceId));

    }

    @GetMapping("/reference/{referenceId}/photos")
    public ResponseEntity<List<MediaResponseDTO>> getPhotosByReferenceId(@PathVariable Long referenceId){
        return ResponseEntity.ok(mediaService.getPhotosByReferenceId(referenceId));
    }

    @GetMapping("/reference/{referenceId}/videos")
    public ResponseEntity<List<MediaResponseDTO>> getVideoByReferenceId(@PathVariable Long referenceId){
        return ResponseEntity.ok(mediaService.getVideosByReferenceId(referenceId));
    }

    @GetMapping("/referenceId/{referenceId}/referenceType/{referenceType}")
    public ResponseEntity<List<MediaResponseDTO>> grtMediaByReferenceIdAndReferenceType(@PathVariable Long referenceId , @PathVariable String referenceType){
        return ResponseEntity.ok(mediaService.getMediaByReferenceIdAndType(referenceId,referenceType));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMedia(@PathVariable Long id){
        mediaService.deleteMedia(id);
        return ResponseEntity.ok("Media deleted successfully");
    }

    @GetMapping("/refernceId/{referenceId}/referenceType/{referenceType}/mediaType/{mediaType}")
    public ResponseEntity<List<MediaResponseDTO>> getMediaByRefIdAndRefTypeAndMediaType(@PathVariable Long referenceId , @PathVariable String referenceType , @PathVariable MediaType mediaType){
        return ResponseEntity.ok(mediaService.findByRefIdANdRefTypAndMediaType(referenceId , referenceType , mediaType));
    }



}
