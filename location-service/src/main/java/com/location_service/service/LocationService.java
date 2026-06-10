package com.location_service.service;


import com.location_service.dto.LocationRequestDTO;
import com.location_service.dto.LocationResponseDTO;

import java.util.List;

public interface LocationService {

    LocationResponseDTO addLocation(LocationRequestDTO dto);
    LocationResponseDTO getLocationById(Long id);
    LocationResponseDTO getLocationByReferenceIdAndType(Long referenceId , String referenceType);

    List<LocationResponseDTO> getLocationByCityAndReferenceType(String city , String referenceType);
    List<LocationResponseDTO> getLocationByDistrictAndReferenceType(String district , String referenceType);
    List<LocationResponseDTO>  getNearByFarmers(
            Double latitude , Double longitude , Double radiusKm

    );
    LocationResponseDTO updateLocation(Long id, LocationRequestDTO dto);
    void deleteLocation(Long id);

}
