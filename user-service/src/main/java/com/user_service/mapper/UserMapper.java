package com.user_service.mapper;

import com.user_service.dto.UserRequestDTO;
import com.user_service.dto.UserResponseDTO;
import com.user_service.model.User;
import com.user_service.model.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequestDTO dto){
        return User.builder()
                .fullName(dto.getFullNme())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .phone(dto.getPhone())
                .city(dto.getCity())
                .district(dto.getDistrict())
                .role(UserRole.CONSUMER)
                .build();
    }

    public UserResponseDTO toDTo(User user){
        return UserResponseDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .city(user.getCity())
                .district(user.getDistrict())
                .role(user.getRole())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
