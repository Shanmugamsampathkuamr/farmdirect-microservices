package com.admin_service.controller;

import com.admin_service.dto.*;
import com.admin_service.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    @PostMapping("/register")
    public ResponseEntity<AdminResponseDTO> createAdmin(@Valid @RequestBody AdminRequestDTO requestDTO){
        return new ResponseEntity<>(adminService.createAdmin(requestDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(adminService.login(loginRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminResponseDTO> getAdminById(@PathVariable Long id){
        return ResponseEntity.ok(adminService.getAdminById(id));
    }

    @GetMapping("/{email}")
    public ResponseEntity<AdminResponseDTO> getAdminByEmail(@PathVariable String email){
        return ResponseEntity.ok(adminService.getAdminByEmail(email));
    }

    @GetMapping
    public ResponseEntity<List<AdminResponseDTO>> getAllAdmin(){
        return  ResponseEntity.ok(adminService.getAllAdmins());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminResponseDTO> updateAdmin(@PathVariable Long id , @RequestBody AdminRequestDTO requestDTO){
        return ResponseEntity.ok(adminService.updateAdmin(id , requestDTO));
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivateAdmin(@PathVariable Long id){
         adminService.deactivateAdmin(id);
         return ResponseEntity.ok("Admin deactivated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long id){
        adminService.deleteAdmin(id);
        return ResponseEntity.ok("Admin deleted successfully");
    }

    @PutMapping("/farmers/{farmerId}/approve")
    public ResponseEntity<FarmerResponseDTO> approveFarmer(@PathVariable Long farmerId){
        return ResponseEntity.ok(adminService.approveFarmer(farmerId));
    }

    @PutMapping("/farmers/{farmerId}/reject")
    public ResponseEntity<FarmerResponseDTO> rejectFarmer(@PathVariable Long  farmerId){
        return ResponseEntity.ok(adminService.rejectFarmer(farmerId));

    }

    @PutMapping("/runners/{runnerId}/approve")
    public ResponseEntity<RunnerResponseDTO> approveRunner(@PathVariable Long runnerId){
        return ResponseEntity.ok(adminService.approveRunner(runnerId));
    }


    @PutMapping("/runners/{runnerId}/reject")
    public ResponseEntity<RunnerResponseDTO> rejectRunner(@PathVariable Long  runnerId){
        return ResponseEntity.ok(adminService.rejectRunner(runnerId));

    }

}
