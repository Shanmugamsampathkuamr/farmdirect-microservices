package com.farmer_service.service;

import com.farmer_service.dto.*;
import com.farmer_service.exception.FarmerAlreadyExistsException;
import com.farmer_service.exception.FarmerNotFoundException;
import com.farmer_service.feignclient.LocationFeignClint;
import com.farmer_service.mapper.FarmerMapper;
import com.farmer_service.model.Farmer;
import com.farmer_service.model.FarmerStatus;
import com.farmer_service.model.FarmerVerificationStatus;
import com.farmer_service.repository.FarmerRepository;
import com.farmer_service.utile.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FarmerServiceImpl implements FarmerService {



    private final FarmerRepository farmerRepository;
    private final FarmerMapper farmerMapper;
    private final PasswordEncoder passwordEncoder;
    private final LocationFeignClint locationFeignClient;
    private final JwtUtil jwtUtil;


    @Override
    public FarmerResponseDTo registerFarmer(FarmerRequestDTO requestDTO) {
        if (farmerRepository.existsByEmail(requestDTO.getEmail())){
            throw  new FarmerAlreadyExistsException("Email already registered :" + requestDTO.getEmail());

        }
        if (farmerRepository.existsByPhone(requestDTO.getPhone())){
            throw  new FarmerAlreadyExistsException("phone already registered :" + requestDTO.getPhone());
        }
        if (farmerRepository.existsByAadharNumber(requestDTO.getAadharNumber())){
            throw  new FarmerAlreadyExistsException("aadhar Number already registered :" + requestDTO.getAadharNumber());
        }

        Farmer farmer = farmerMapper.toEntity(requestDTO);
        farmer.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        Farmer savedFarmer = farmerRepository.save(farmer);

        try{
            LocationRequestDTO locationRequestDTO = LocationRequestDTO.builder()
                    .referenceId(savedFarmer.getId())
                    .referenceType("FARMER")
                    .city(requestDTO.getCity())
                    .district(requestDTO.getDistrict())
                    .state(requestDTO.getState())
                    .village(requestDTO.getVillage())
                    .latitude(requestDTO.getLatitude())
                    .longitude(requestDTO.getLongitude())
                    .pincode(requestDTO.getPincode())
                    .build();
            locationFeignClient.saveLocation(locationRequestDTO);
            log.info("Location saved for farmer id:{}",savedFarmer.getId());
        }catch (Exception e){
            log.error("Location save failed for farmer:{}",e.getMessage());
        }


         return farmerMapper.toDTO(savedFarmer);
    }

    @Override
    public FarmerResponseDTo getFarmerById(Long id) {
        Farmer farmer = farmerRepository.findById(id)
                .orElseThrow(()->new FarmerNotFoundException("Farmer not found with id" + id));
        return farmerMapper.toDTO(farmer);
    }

    @Override
    public FarmerResponseDTo getFarmerByEmail(String email) {
        Farmer farmer = farmerRepository.findByEmail(email)
                .orElseThrow(()-> new FarmerNotFoundException ("farmer  not  found with this email " + email) );
        return farmerMapper.toDTO(farmer);

    }

    @Override
    public List<FarmerResponseDTo> getAllFarmer() {
        return farmerRepository.findAll()
                .stream()
                .map(farmerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FarmerResponseDTo> getPendingFarmer() {
        return farmerRepository.findByVerificationStatus(FarmerVerificationStatus.PENDING)
                .stream()
                .map(farmerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FarmerResponseDTo> getFarmerByCity(String city) {
        return farmerRepository.findByCity(city)
                .stream()
                .map(farmerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FarmerResponseDTo> getFarmerByDistrict(String district) {
        return farmerRepository.findByDistrict(district)
                .stream()
                .map(farmerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FarmerResponseDTo updateFarmer(Long id, FarmerRequestDTO requestDTO) {
        Farmer farmer = farmerRepository.findById(id)
                .orElseThrow(()->new FarmerNotFoundException("Farmer not fount with id:" + id));
        farmer.setFullName(requestDTO.getFullName());
        farmer.setPhone(requestDTO.getPhone());
        farmer.setVillage(requestDTO.getVillage());
        farmer.setCity(requestDTO.getCity());
        farmer.setDistrict(requestDTO.getDistrict());
        farmer.setFarmSizeInAcer(requestDTO.getFarmSizeInAcres());

        return farmerMapper.toDTO(farmerRepository.save(farmer));
    }

    @Override
    public FarmerResponseDTo approveFarmer(Long id) {
        Farmer farmer = farmerRepository.findById(id)
                .orElseThrow(()-> new FarmerNotFoundException("Farmer not found with id:"+id));

        farmer.setVerificationStatus(FarmerVerificationStatus.APPROVED);
        farmer.setState(String.valueOf(FarmerStatus.ACTIVE));
        return farmerMapper.toDTO(farmerRepository.save(farmer));
    }

    @Override
    public FarmerResponseDTo rejectFarmer(Long id) {
        Farmer farmer = farmerRepository.findById(id)
                .orElseThrow(()-> new FarmerNotFoundException("Farmer not found with id:"+id));

        farmer.setVerificationStatus(FarmerVerificationStatus.REJECTED);
        farmer.setState(String.valueOf(FarmerStatus.INACTIVE));
        return farmerMapper.toDTO(farmerRepository.save(farmer));
    }

    @Override
    public FarmerResponseDTo updateAadharDocument(Long id, String aadharDocumentPath) {
        Farmer farmer = farmerRepository.findById(id)
                .orElseThrow(()->new FarmerNotFoundException("Farmer not fount with id:"+id));
        farmer.setAadharDocumentPath(aadharDocumentPath);
        return farmerMapper.toDTO(farmerRepository.save(farmer));
    }

    @Override
    public FarmerResponseDTo updateFarmPhoto(Long id, String farmPhotoPath) {
        Farmer farmer = farmerRepository.findById(id)
                .orElseThrow(()->new FarmerNotFoundException("Farmer not fount with id:"+id));
        farmer.setFarmPhotoPath(farmPhotoPath);
        return farmerMapper.toDTO(farmerRepository.save(farmer));
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO requestDTO) {
       Farmer farmer = farmerRepository.findByEmail(requestDTO.getEmail())
               .orElseThrow(()->new FarmerNotFoundException("Invalid email or password"));

       if (!passwordEncoder.matches(requestDTO.getPassword(),farmer.getPassword())){
           throw new FarmerNotFoundException("Invalid email or password");
       }

       String token = jwtUtil.generateToken(farmer.getEmail(),"FARMER",farmer.getId());

       return LoginResponseDTO.builder()
               .id(farmer.getId())
               .fullName(farmer.getFullName())
               .email(farmer.getEmail())
               .role("FARMER")
               .token(token)
               .build();
    }

    @Override
    public void deleteFormer(Long id) {

        if (!farmerRepository.existsById(id)){
            throw new FarmerNotFoundException("Farmer not found with id:"+id);
        }
        farmerRepository.deleteById(id);

    }


}
