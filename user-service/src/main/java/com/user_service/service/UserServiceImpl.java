package com.user_service.service;

import com.user_service.dto.*;
import com.user_service.exception.UserAlreadyExistsException;
import com.user_service.exception.UserNotFoundException;
import com.user_service.feginclient.LocationClient;
import com.user_service.mapper.UserMapper;
import com.user_service.model.User;
import com.user_service.repository.UserRepository;
import com.user_service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final LocationClient locationClient;
    private final JwtUtil jwtUtil;


    @Override
    public UserResponseDTO registerUser(UserRequestDTO requestDTO) {
        if (userRepository.existsByEmail(requestDTO.getEmail())){
            throw new UserAlreadyExistsException("Email already registed:"+requestDTO.getEmail());
        }
        if (userRepository.existsByPhone(requestDTO.getPhone())){
            throw new UserAlreadyExistsException("Phone already registerd:"+requestDTO.getPhone());
        }

        User user = userMapper.toEntity(requestDTO);
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        User savedUser = userRepository.save(user);

        try{
            LocationRequestDTO locationRequestDTO =LocationRequestDTO.builder()
                    .referenceId(savedUser.getId())
                    .referenceType("USER")
                    .city(requestDTO.getCity())
                    .district(requestDTO.getDistrict())
                    .state(requestDTO.getState())
                    .village(requestDTO.getVillage())
                    .latitude(requestDTO.getLatitude())
                    .longitude(requestDTO.getLongitude())
                    .pincode(requestDTO.getPincode())
                    .build();
            locationClient.saveLocation(locationRequestDTO);
            log.info("Location saved for user id:{}",savedUser.getId());
        } catch (Exception e) {
            log.error("Location save failed for user:{}",e.getMessage());
        }


        return userMapper.toDTo(savedUser);

    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User not found with id :"+ id));
        return userMapper.toDTo(user);
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException("User not found with id :"+ email));
        return userMapper.toDTo(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
       return userRepository.findAll()
               .stream()
               .map(userMapper::toDTo)
               .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO requestDTO) {
       User user = userRepository.findById(id)
               .orElseThrow(()->new UserNotFoundException("User not found with id :"+id));
       user.setFullName(requestDTO.getFullNme());
       user.setPhone(requestDTO.getPhone());
       user.setCity(requestDTO.getCity());
       user.setDistrict(requestDTO.getDistrict());
       User updated = userRepository.save(user);
       return userMapper.toDTo(updated);
    }

    @Override
    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)){
            throw new UserNotFoundException("User not found with id :"+id);
        }

        userRepository.deleteById(id);

    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
       User user = userRepository.findByEmail(loginRequestDTO.getEmail())
               .orElseThrow(()->new UserNotFoundException("Invalid email or password"));
       if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())){
           throw new UserNotFoundException("Invalid email or password");
       }
       String token = jwtUtil.generateToken(
               user.getEmail(),
               user.getRole().name(),
               user.getId()
       );
       return LoginResponseDTO.builder()
               .id(user.getId())
               .fullName(user.getFullName())
               .email(user.getEmail())
               .role(user.getRole().name())
               .token(token)
               .build();
    }
}
