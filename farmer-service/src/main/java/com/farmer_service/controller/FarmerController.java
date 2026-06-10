package com.farmer_service.controller;


import com.farmer_service.dto.FarmerRequestDTO;
import com.farmer_service.dto.FarmerResponseDTo;
import com.farmer_service.dto.LoginRequestDTO;
import com.farmer_service.dto.LoginResponseDTO;
import com.farmer_service.model.Farmer;
import com.farmer_service.service.FarmerService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farmers")
@RequiredArgsConstructor
public class FarmerController {

    private final FarmerService farmerService;

    @PostMapping("/register")
    public ResponseEntity<FarmerResponseDTo> register(@Valid @RequestBody FarmerRequestDTO requestDTO){
        return  new ResponseEntity<>(farmerService.registerFarmer(requestDTO), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO requestDTO) {
        return ResponseEntity.ok(farmerService.login(requestDTO));
    }



    @GetMapping("/{id}")
    public ResponseEntity<FarmerResponseDTo> getFarmerById(@PathVariable Long id){
        return ResponseEntity.ok(farmerService.getFarmerById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<FarmerResponseDTo> getFarmerByEmail(@PathVariable String email){
        return ResponseEntity.ok(farmerService.getFarmerByEmail(email));
    }

    @GetMapping
    public ResponseEntity<List<FarmerResponseDTo>> getAllFarmers(){
        return ResponseEntity.ok(farmerService.getAllFarmer());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<FarmerResponseDTo>> getPendingFarmer(){
        return ResponseEntity.ok(farmerService.getPendingFarmer());

    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<FarmerResponseDTo>> getFarmerByCity(@PathVariable String city){
        return ResponseEntity.ok(farmerService.getFarmerByCity(city));
    }

    @GetMapping("/district/{district}")
    public ResponseEntity<List<FarmerResponseDTo>> getFarmerByDistrict(@PathVariable String district){
        return ResponseEntity.ok(farmerService.getFarmerByDistrict(district));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FarmerResponseDTo> updateFarmer(@PathVariable Long id , @Valid @RequestBody FarmerRequestDTO requestDTO
    ){
        return ResponseEntity.ok(farmerService.updateFarmer(id ,  requestDTO));
    }


    @PutMapping("/approve/{id}")
    public ResponseEntity<FarmerResponseDTo> approveFarmer(@PathVariable Long id){
        return ResponseEntity.ok(farmerService.approveFarmer(id));
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<FarmerResponseDTo> rejectFarmer(@PathVariable Long id){
        return ResponseEntity.ok(farmerService.rejectFarmer(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFarmer(@PathVariable Long id){

        farmerService.deleteFormer(id);

        return ResponseEntity.ok("Farmer deleted successfully");

    }

    @PutMapping("/{id}/aadhar-document")
    public ResponseEntity<FarmerResponseDTo> updateAadharDocument(@PathVariable Long id , @RequestParam String aadharDocumentPath){
        return ResponseEntity.ok(farmerService.updateAadharDocument(id,aadharDocumentPath));
    }

    @PutMapping("/{id}/farm-photo")
    public ResponseEntity<FarmerResponseDTo> updateFarmPhoto(
            @PathVariable Long id,
            @RequestParam String farmPhotoPath
    ){
        return ResponseEntity.ok(farmerService.updateFarmPhoto(id,farmPhotoPath));
    }
}
