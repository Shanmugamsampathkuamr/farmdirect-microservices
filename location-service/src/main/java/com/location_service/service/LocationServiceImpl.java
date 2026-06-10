package com.location_service.service;

import com.location_service.dto.LocationRequestDTO;
import com.location_service.dto.LocationResponseDTO;
import com.location_service.exception.LocationNotFoundException;
import com.location_service.mapper.LocationMapper;
import com.location_service.model.Location;
import com.location_service.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService{

    private final LocationRepository locationRepository;
    private  final LocationMapper locationMapper;

    @Override
    public LocationResponseDTO addLocation(LocationRequestDTO dto) {

        Location location = locationMapper.toEntity(dto);
        return locationMapper.toDTO(locationRepository.save(location));

    }

    @Override
    public LocationResponseDTO getLocationById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(()->new LocationNotFoundException("location not found with id"+id));
        return locationMapper.toDTO(location);
    }

    @Override
    public LocationResponseDTO getLocationByReferenceIdAndType(Long referenceId, String referenceType) {
        Location location = locationRepository.findByReferenceIdAndReferenceType(referenceId,referenceType)
                .orElseThrow(()->new LocationNotFoundException("Location not found with this :"+referenceType+"id:"+referenceId));
        return locationMapper.toDTO(location);
    }

    @Override
    public List<LocationResponseDTO> getLocationByCityAndReferenceType(String city, String referenceType) {
        return locationRepository
                .findByCityAndReferenceType(city , referenceType)
                .stream()
                .map(locationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LocationResponseDTO> getLocationByDistrictAndReferenceType(String district, String referenceType) {
        return locationRepository
                .findByDistrictAndReferenceType(district , referenceType)
                .stream()
                .map(locationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LocationResponseDTO> getNearByFarmers(Double latitude, Double longitude, Double radiusKm) {
        return locationRepository
                .findNearbyLocations(latitude,longitude,radiusKm,"FARMER")
                .stream()
                .map(locationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LocationResponseDTO updateLocation(Long id, LocationRequestDTO dto) {
         Location location = locationRepository.findById(id)
                 .orElseThrow(()->new  LocationNotFoundException("Location not found with id:"+id));
         location.setCity(dto.getCity());
         location.setDistrict(dto.getDistrict());
         location.setState(dto.getState());
         location.setVillage(dto.getVillage());
        location.setPincode(dto.getPincode());
        location.setLatitude(dto.getLatitude());
        location.setLongitude(dto.getLongitude());
        return locationMapper.toDTO(locationRepository.save(location));
    }

    @Override
    public void deleteLocation(Long id) {
        if (!locationRepository.existsById(id)){
            throw new LocationNotFoundException("Location not found with id:"+id);

        }
        locationRepository.deleteById(id);

    }
}
