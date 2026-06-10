package com.admin_service.mapper;

import com.admin_service.dto.AdminRequestDTO;
import com.admin_service.dto.AdminResponseDTO;
import com.admin_service.model.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    public Admin toEntity(AdminRequestDTO dto)
    {
        return Admin.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .build();


    }

    public AdminResponseDTO toDTO(Admin admin){
        return AdminResponseDTO.builder()
                .id(admin.getId())
                .fullName(admin.getFullName())
                .email(admin.getEmail())
                .phone(admin.getPhone())
                .role(admin.getRole())
                .isActive(admin.isActive())
                .createdAt(admin.getCreatedAt())
                .build();
    }
}
