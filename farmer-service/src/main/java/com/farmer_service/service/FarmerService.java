package com.farmer_service.service;

import com.farmer_service.dto.FarmerRequestDTO;
import com.farmer_service.dto.FarmerResponseDTo;
import com.farmer_service.dto.LoginRequestDTO;
import com.farmer_service.dto.LoginResponseDTO;
import com.farmer_service.model.Farmer;

import java.util.List;

public interface FarmerService {

    FarmerResponseDTo registerFarmer(FarmerRequestDTO requestDTO);
    FarmerResponseDTo getFarmerById(Long id);
    FarmerResponseDTo getFarmerByEmail(String email);
    List<FarmerResponseDTo> getAllFarmer();
    List<FarmerResponseDTo> getPendingFarmer();
    List<FarmerResponseDTo> getFarmerByCity(String city);
    List<FarmerResponseDTo> getFarmerByDistrict(String district);
    FarmerResponseDTo updateFarmer(Long id , FarmerRequestDTO requestDTO );
    FarmerResponseDTo approveFarmer(Long id);
    FarmerResponseDTo rejectFarmer(Long id);
    FarmerResponseDTo updateAadharDocument(Long id , String aadharDocumentPath);
    FarmerResponseDTo updateFarmPhoto(Long id , String farmPhotoPath);
    // Add login method
    LoginResponseDTO login(LoginRequestDTO requestDTO);
    void deleteFormer(Long id);

}
