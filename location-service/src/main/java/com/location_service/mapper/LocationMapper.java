package com.location_service.mapper;

import com.location_service.dto.LocationRequestDTO;
import com.location_service.dto.LocationResponseDTO;
import com.location_service.model.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    public Location toEntity(LocationRequestDTO dto){

        return Location.builder()
                .referenceId(dto.getReferenceId())
                .referenceType(dto.getReferenceType())
                .city(dto.getCity())
                .district(dto.getDistrict())
                .state(dto.getState())
                .village(dto.getVillage())
                .pincode(dto.getPincode())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();

    }

    public LocationResponseDTO toDTO(Location location){
        return LocationResponseDTO.builder()
                .id(location.getId())
                .referenceId(location.getReferenceId())
                .referenceType(location.getReferenceType())
                .city(location.getCity())
                .district(location.getDistrict())
                .state(location.getState())
                .village(location.getVillage())
                .pincode(location.getPincode())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .createdAt(location.getCreatedAt())
                .build();
    }

}
