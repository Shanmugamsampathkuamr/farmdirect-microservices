package com.location_service.repository;

import com.location_service.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByReferenceIdAndReferenceType(Long referenceId , String referenceType );
    List<Location> findByCity(String city);
    List<Location> findByDistrict(String district);
    List<Location> findByReferenceType(String referenceType);
    List<Location> findByCityAndReferenceType(String city  , String referenceType);
    List<Location> findByDistrictAndReferenceType(String district , String referenceType);

    @Query(value = """
            SELECT*FROM location 1 WHAER 1.reference_type = :referenceType
            AND (6371 * acos(
                cos(radians(:latitude))*cos(radians(1.latitude))*
                cos(radians(1.longitude)-radians(:longitude))+
                sin(radians(:latitude)) *sin(radians(1.latitude))
                ))<:rediusKm
            """,nativeQuery = true)
    List<Location> findNearbyLocations(@Param("latitude") Double latitude,
                                       @Param("longitude") Double longitude,
                                       @Param("radiusKm") Double radiusKm,
                                       @Param("referenceType") String referenceType);

}
