package com.runner_service.service;



import com.runner_service.dto.LoginRequestDTO;
import com.runner_service.dto.LoginResponseDTO;
import com.runner_service.dto.RunnerRequestDTO;
import com.runner_service.dto.RunnerResponseDTO;

import java.util.List;

public interface RunnerService {
    RunnerResponseDTO registerRunner(RunnerRequestDTO requestDTO);
    RunnerResponseDTO getRunnerById(Long id);
    RunnerResponseDTO getRunnerByEmail(String email);
    List<RunnerResponseDTO> getAllRunners();
    List<RunnerResponseDTO> getPendingRunners();
    List<RunnerResponseDTO> getRunnersByCity(String city);
    List<RunnerResponseDTO> getActiveRunners();
    RunnerResponseDTO updateRunner(Long id, RunnerRequestDTO requestDTO);
    RunnerResponseDTO approveRunner(Long id);
    RunnerResponseDTO rejectRunner(Long id);
    RunnerResponseDTO updateStatus(Long id , String status);
    RunnerResponseDTO updateAadharDocument(Long id , String aadharDocumentPath);
    RunnerResponseDTO updateLicenseDocument(Long id , String licenseDocumentPath);
    // Add login method
    LoginResponseDTO login(LoginRequestDTO requestDTO);
    void deleteRunner(Long id);
}
