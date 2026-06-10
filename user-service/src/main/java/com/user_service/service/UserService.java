package com.user_service.service;

import com.user_service.dto.LoginRequestDTO;
import com.user_service.dto.LoginResponseDTO;
import com.user_service.dto.UserRequestDTO;
import com.user_service.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO registerUser(UserRequestDTO requestDTO);
    UserResponseDTO getUserById(Long id);
    UserResponseDTO getUserByEmail(String email);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO updateUser(Long id , UserRequestDTO requestDTO);
    void deleteUser (Long id);
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

}
