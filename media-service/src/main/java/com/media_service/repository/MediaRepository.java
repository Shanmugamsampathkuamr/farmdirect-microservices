package com.media_service.repository;

import com.media_service.model.Media;
import com.media_service.model.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media,Long> {

    List<Media> findByReferenceId(Long referenceId);

    List<Media> findByReferenceIdAndReferenceType(Long referenceId , String referenceType);

    List<Media> findByReferenceIdAndMediaType(Long referenceId, MediaType mediaType);

    List<Media> findByReferenceIdAndReferenceTypeAndMediaType(Long referenceId , String referenceType , MediaType mediaType);

    void deleteByPublicId(String publicId);

}


