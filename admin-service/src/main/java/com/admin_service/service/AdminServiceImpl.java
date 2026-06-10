package com.admin_service.service;


import com.admin_service.dto.*;
import com.admin_service.exception.AdminAlreadyExistsException;
import com.admin_service.exception.AdminNotFoundException;
import com.admin_service.feginclient.FarmerFeignClient;
import com.admin_service.feginclient.NotificationFeignClient;
import com.admin_service.feginclient.RunnerFeignClient;
import com.admin_service.kafka.NotificationEvent;
import com.admin_service.kafka.NotificationProducer;
import com.admin_service.mapper.AdminMapper;
import com.admin_service.model.Admin;
import com.admin_service.repository.AdminRepository;
import com.admin_service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {



    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;
    private final FarmerFeignClient farmerFeignClient;
    private final RunnerFeignClient runnerFeignClient;
    private final NotificationProducer notificationProducer;
    private final JwtUtil jwtUtil;



    @Override
    public AdminResponseDTO createAdmin(AdminRequestDTO requestDTO) {
       if (adminRepository.existsByEmail(requestDTO.getEmail())){
           throw new AdminAlreadyExistsException("Email already registered");
       }
       if (adminRepository.existsByPhone(requestDTO.getPhone())){
           throw new AdminAlreadyExistsException("Phone already registered");
       }

        Admin admin = adminMapper.toEntity(requestDTO);
        admin.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        return adminMapper.toDTO(adminRepository.save(admin));

    }

    @Override
    public AdminResponseDTO getAdminById(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(()->new AdminNotFoundException("Admin not fount with id:"+id));
        return adminMapper.toDTO(admin);
    }

    @Override
    public AdminResponseDTO getAdminByEmail(String email) {
       Admin admin = adminRepository.findByEmail(email)
               .orElseThrow(()->new AdminNotFoundException("Admin not found with email:"+email));
       return adminMapper.toDTO(admin);
    }

    @Override
    public List<AdminResponseDTO> getAllAdmins() {
        return adminRepository.findAll()
                .stream()
                .map(adminMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AdminResponseDTO updateAdmin(Long id, AdminRequestDTO requestDTO) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(()->new AdminNotFoundException("Admin not found with id:"+id));
        admin.setFullName(requestDTO.getFullName());
        admin.setPhone(requestDTO.getPhone());
        return adminMapper.toDTO(adminRepository.save(admin));

    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Admin admin = adminRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(()->new AdminNotFoundException("Admin not found"));
        if (!passwordEncoder.matches(loginRequestDTO.getPassword(),admin.getPassword())){
            throw new AdminNotFoundException("Invalid email or password");
        }
        String token = jwtUtil.generateToken(admin.getEmail(),"ADMIN",admin.getId());

        return LoginResponseDTO.builder()
                .id(admin.getId())
                .fullName(admin.getFullName())
                .email(admin.getEmail())
                .role("ADMIN")
                .token(token)
                .build();
    }

    @Override
    public void deactivateAdmin(Long Id) {

        Admin admin = adminRepository.findById(Id)
                .orElseThrow(()->new AdminNotFoundException("Admin not found with id:"+Id));
        admin.setActive(false);
        adminRepository.deleteById(Id);


    }

    @Override
    public void deleteAdmin(Long id) {

        if (!adminRepository.existsById(id)){
            throw  new AdminNotFoundException("Admin not found with id:"+id);
        }
        adminRepository.deleteById(id);

    }

    @Override
    public FarmerResponseDTO approveFarmer(Long farmerId) {
        FarmerResponseDTO farmer = farmerFeignClient.approveFarmer(farmerId);
        try{
            notificationProducer.sendNotification(
                    NotificationEvent.builder()
                            .receiverId(farmerId)
                            .receiverType("FARMER")
                            .title("Account Approved!")
                            .message("Congratulations! Your farmer account has been approved. You can now post your crops!")
                            .notificationType("REGISTRATION_APPROVED")
                            .referenceId(farmerId)
                            .build()
            );
        }catch (Exception e){
            log.error("notification failed for farmer approval:{}",e.getMessage());
        }
        return farmer;
    }

    @Override
    public FarmerResponseDTO rejectFarmer(Long farmerId) {
        FarmerResponseDTO farmer = farmerFeignClient.rejectFarmer(farmerId);
        try {
            notificationProducer.sendNotification(
                    NotificationEvent.builder()
                            .receiverId(farmerId)
                            .receiverType("FARMER")
                            .title("Account Rejected")
                            .message("Sorry! Your farmer account registration has been rejected. please contact support")
                            .notificationType("REGISTRATION_REJECTED")
                            .referenceId(farmerId)
                            .build()
            );

        }catch (Exception e){
            log.error("Notification failed for former rejection:{}",e.getMessage());
        }
        return farmer;
    }

    @Override
    public RunnerResponseDTO approveRunner(Long runnerId) {
        RunnerResponseDTO runner = runnerFeignClient.approveRunner(runnerId);
        try {
            notificationProducer.sendNotification(
                    NotificationEvent.builder()
                            .receiverId(runnerId)
                            .receiverType("RUNNER")
                            .title("Account Approved")
                            .message("Congratulations! Your runner account has been approved. you can now start delivering!")
                            .notificationType("REGISTRATION_APPROVED")
                            .referenceId(runnerId)
                            .build()
            );

        }catch (Exception e){
            log.error("Notification failed for runner approval:{}",e.getMessage());
        }
        return runner;
    }

    @Override
    public RunnerResponseDTO rejectRunner(Long runnerId) {
        RunnerResponseDTO runner = runnerFeignClient.rejectRunner(runnerId);
        try {
            notificationProducer.sendNotification(
                    NotificationEvent.builder()
                            .receiverId(runnerId)
                            .receiverType("RUNNER")
                            .title("Account Rejected")
                            .message("Sorry! Your runner account registration has been rejected. please contact support")
                            .notificationType("REGISTRATION_REJECTED")
                            .referenceId(runnerId)
                            .build()
            );

        }catch (Exception e){
            log.error("Notification failed for runner rejection:{}",e.getMessage());
        }
        return runner;
    }
}
