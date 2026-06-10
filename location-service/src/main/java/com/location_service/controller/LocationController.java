package com.location_service.controller;

import com.location_service.dto.LocationRequestDTO;
import com.location_service.dto.LocationResponseDTO;
import com.location_service.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;


    @PostMapping("/addLocation")
    public ResponseEntity<LocationResponseDTO> addLocation( @Valid @RequestBody LocationRequestDTO dto){
        return new ResponseEntity<>(locationService.addLocation(dto), HttpStatus.CREATED);

    }
    @GetMapping("/getLocationId/{id}")
    public ResponseEntity<LocationResponseDTO> getLocationById(@PathVariable Long locationId){
        return ResponseEntity.ok(locationService.getLocationById(locationId));
    }

    @GetMapping("/reference")
    public ResponseEntity<LocationResponseDTO> getLocationByReferenceAndType(@PathVariable Long referenceId , @RequestParam String referenceTyp){
        return ResponseEntity.ok(locationService.getLocationByReferenceIdAndType(referenceId , referenceTyp));
    }

    @GetMapping("/city/{city}")
        public ResponseEntity<List<LocationResponseDTO>> getLocationByCity(@PathVariable String city , @RequestParam String referenceType){
        return ResponseEntity.ok(locationService.getLocationByCityAndReferenceType(city,referenceType));
    }

    @GetMapping("/district/{district}")
    public ResponseEntity<List<LocationResponseDTO>> getLocationByDistrictAndReferenceType(@PathVariable String district , @RequestParam String referenceType){
        return ResponseEntity.ok(locationService.getLocationByDistrictAndReferenceType(district , referenceType));
    }

    @GetMapping("/farmers/nearby")
    public ResponseEntity<List<LocationResponseDTO>> getNearByFarmers(@RequestParam Double latitude, @RequestParam Double Longitude , @RequestParam(defaultValue = "20") Double radiusKm){
        return ResponseEntity.ok(locationService.getNearByFarmers(latitude , Longitude , radiusKm));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LocationResponseDTO> updateLocation(@PathVariable Long id , @Valid@RequestBody LocationRequestDTO requestDTO){
        return ResponseEntity.ok(locationService.updateLocation(id , requestDTO));
    }

    @DeleteMapping("/delete{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable Long id){
        locationService.deleteLocation(id);
        return ResponseEntity.ok("Location deleted successfully");
    }
}
