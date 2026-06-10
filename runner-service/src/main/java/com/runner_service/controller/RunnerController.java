package com.runner_service.controller;


import com.runner_service.dto.LoginRequestDTO;
import com.runner_service.dto.LoginResponseDTO;
import com.runner_service.dto.RunnerRequestDTO;
import com.runner_service.dto.RunnerResponseDTO;
import com.runner_service.service.RunnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/runners")
@RequiredArgsConstructor
public class RunnerController {

    private final RunnerService runnerService;

    @PostMapping("/register")
    public ResponseEntity<RunnerResponseDTO> register(@Valid @RequestBody RunnerRequestDTO requestDTO) {
        return new ResponseEntity<>(runnerService.registerRunner(requestDTO), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO requestDTO) {
        return ResponseEntity.ok(runnerService.login(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RunnerResponseDTO> getRunnerById(@PathVariable Long id) {
        return ResponseEntity.ok(runnerService.getRunnerById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<RunnerResponseDTO> getRunnerByEmail(@PathVariable String email) {
        return ResponseEntity.ok(runnerService.getRunnerByEmail(email));
    }

    @GetMapping
    public ResponseEntity<List<RunnerResponseDTO>> getAllRunners() {
        return ResponseEntity.ok(runnerService.getAllRunners());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<RunnerResponseDTO>> getPendingRunners() {
        return ResponseEntity.ok(runnerService.getPendingRunners());
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<RunnerResponseDTO>> getRunnersByCity(@PathVariable String city) {
        return ResponseEntity.ok(runnerService.getRunnersByCity(city));
    }

    @GetMapping("/active")
    public ResponseEntity<List<RunnerResponseDTO>> getActiveRunners() {
        return ResponseEntity.ok(runnerService.getActiveRunners());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RunnerResponseDTO> updateRunner(@PathVariable Long id,
                                                          @Valid @RequestBody RunnerRequestDTO requestDTO) {
        return ResponseEntity.ok(runnerService.updateRunner(id, requestDTO));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<RunnerResponseDTO> approveRunner(@PathVariable Long id) {
        return ResponseEntity.ok(runnerService.approveRunner(id));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<RunnerResponseDTO> rejectRunner(@PathVariable Long id) {
        return ResponseEntity.ok(runnerService.rejectRunner(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRunner(@PathVariable Long id) {
        runnerService.deleteRunner(id);
        return ResponseEntity.ok("Runner deleted successfully");
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<RunnerResponseDTO> updateRunnerStatus(@PathVariable Long id, @RequestParam String status){
        return ResponseEntity.ok(runnerService.updateStatus(id,status));
    }

    @PutMapping("/{id}/aadhar-document")
    public ResponseEntity<RunnerResponseDTO> updateAadharDocument(@PathVariable Long id,@RequestParam String aadharDocumentPath ){
        return ResponseEntity.ok(runnerService.updateAadharDocument(id,aadharDocumentPath));
    }

    @PutMapping("/{id}/license-document")
    public ResponseEntity<RunnerResponseDTO> updateLicenseDocument(@PathVariable Long id,@RequestParam String licenseDocumentPath ){
        return ResponseEntity.ok(runnerService.updateAadharDocument(id,licenseDocumentPath));
    }
}
