package com.admin_service.service;

import com.admin_service.dto.*;

import java.util.List;

public interface AdminService {

    AdminResponseDTO createAdmin(AdminRequestDTO requestDTO);
    AdminResponseDTO getAdminById(Long id);
    AdminResponseDTO getAdminByEmail(String email);
    List<AdminResponseDTO> getAllAdmins();
    AdminResponseDTO updateAdmin(Long id , AdminRequestDTO requestDTO);
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
    void deactivateAdmin(Long Id);
    void deleteAdmin(Long id);

    FarmerResponseDTO approveFarmer(Long farmerId);
    FarmerResponseDTO rejectFarmer(Long farmerId);
    RunnerResponseDTO approveRunner(Long runnerId);
    RunnerResponseDTO rejectRunner(Long runnerId);

}
