package com.runner_service.service;


import com.runner_service.dto.*;
import com.runner_service.exception.RunnerAlreadyExistsException;
import com.runner_service.exception.RunnerNotFoundException;
import com.runner_service.feignclient.LocationFeignClient;
import com.runner_service.mapper.RunnerMapper;
import com.runner_service.model.Runner;
import com.runner_service.model.RunnerStatus;
import com.runner_service.model.RunnerVerificationStatus;
import com.runner_service.repository.RunnerRepository;
import com.runner_service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RunnerServiceImpl implements RunnerService {

    private final RunnerRepository runnerRepository;
    private final RunnerMapper runnerMapper;
    private final PasswordEncoder passwordEncoder;
    private final LocationFeignClient locationFeignClient;
    private final JwtUtil jwtUtil;

    @Override
    public RunnerResponseDTO registerRunner(RunnerRequestDTO requestDTO) {
        if (runnerRepository.existsByEmail(requestDTO.getEmail())) {
            throw new RunnerAlreadyExistsException("Email already registered: " + requestDTO.getEmail());
        }
        if (runnerRepository.existsByPhone(requestDTO.getPhone())) {
            throw new RunnerAlreadyExistsException("Phone already registered: " + requestDTO.getPhone());
        }
        if (runnerRepository.existsByAadharNumber(requestDTO.getAadharNumber())) {
            throw new RunnerAlreadyExistsException("Aadhar already registered: " + requestDTO.getAadharNumber());
        }
        if (runnerRepository.existsByLicenseNumber(requestDTO.getLicenseNumber())) {
            throw new RunnerAlreadyExistsException("License already registered: " + requestDTO.getLicenseNumber());
        }
        Runner runner = runnerMapper.toEntity(requestDTO);
        runner.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        Runner savedRunner = runnerRepository.save(runner);

        try {
            LocationRequestDTO locationRequest =LocationRequestDTO.builder()
                    .referenceId(savedRunner.getId())
                    .referenceType("RUNNER")
                    .city(requestDTO.getCity())
                    .district(requestDTO.getDistrict())
                    .state(requestDTO.getState())
                    .village(requestDTO.getVillage())
                    .latitude(requestDTO.getLatitude())
                    .longitude(requestDTO.getLongitude())
                    .pincode(requestDTO.getPincode())
                    .build();

            locationFeignClient.saveLocation(locationRequest);
            log.info("Location saved for runner id:{}",savedRunner.getId());
        } catch (Exception e) {
            log.error("Location save failed for runner{}",e.getMessage());
        }


        return runnerMapper.toDTO(savedRunner);
    }
    @Override
    public LoginResponseDTO login(LoginRequestDTO requestDTO) {
        Runner runner = runnerRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(()->new RunnerNotFoundException("Invalid email or password"));
        if (!passwordEncoder.matches(requestDTO.getPassword(),runner.getPassword())){
            throw new RunnerNotFoundException("Invalid email or password");
        }
        String token = jwtUtil.generateToken(runner.getEmail(),"RUNNER",runner.getId());
        return LoginResponseDTO.builder()
                .id(runner.getId())
                .fullName(runner.getFullName())
                .email(runner.getEmail())
                .role("RUNNER")
                .token(token)
                .build();
    }

    @Override
    public RunnerResponseDTO getRunnerById(Long id) {
        Runner runner = runnerRepository.findById(id)
                .orElseThrow(() -> new RunnerNotFoundException("Runner not found with id: " + id));
        return runnerMapper.toDTO(runner);
    }

    @Override
    public RunnerResponseDTO getRunnerByEmail(String email) {
        Runner runner = runnerRepository.findByEmail(email)
                .orElseThrow(() -> new RunnerNotFoundException("Runner not found with email: " + email));
        return runnerMapper.toDTO(runner);
    }

    @Override
    public List<RunnerResponseDTO> getAllRunners() {
        return runnerRepository.findAll()
                .stream()
                .map(runnerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RunnerResponseDTO> getPendingRunners() {
        return runnerRepository.findByVerificationStatus(RunnerVerificationStatus.PENDING)
                .stream()
                .map(runnerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RunnerResponseDTO> getRunnersByCity(String city) {
        return runnerRepository.findByCity(city)
                .stream()
                .map(runnerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RunnerResponseDTO> getActiveRunners() {
        return runnerRepository.findByStatus(RunnerStatus.ACTIVE)
                .stream()
                .map(runnerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RunnerResponseDTO updateRunner(Long id, RunnerRequestDTO requestDTO) {
        Runner runner = runnerRepository.findById(id)
                .orElseThrow(() -> new RunnerNotFoundException("Runner not found with id: " + id));
        runner.setFullName(requestDTO.getFullName());
        runner.setPhone(requestDTO.getPhone());
        runner.setCity(requestDTO.getCity());
        runner.setDistrict(requestDTO.getDistrict());
        runner.setVehicleType(requestDTO.getVehicleType());
        runner.setVehicleNumber(requestDTO.getVehicleNumber());
        return runnerMapper.toDTO(runnerRepository.save(runner));
    }

    @Override
    public RunnerResponseDTO approveRunner(Long id) {
        Runner runner = runnerRepository.findById(id)
                .orElseThrow(() -> new RunnerNotFoundException("Runner not found with id: " + id));
        runner.setVerificationStatus(RunnerVerificationStatus.APPROVED);
        runner.setStatus(RunnerStatus.ACTIVE);
        return runnerMapper.toDTO(runnerRepository.save(runner));
    }

    @Override
    public RunnerResponseDTO rejectRunner(Long id) {
        Runner runner = runnerRepository.findById(id)
                .orElseThrow(() -> new RunnerNotFoundException("Runner not found with id: " + id));
        runner.setVerificationStatus(RunnerVerificationStatus.REJECTED);
        runner.setStatus(RunnerStatus.INACTIVE);
        return runnerMapper.toDTO(runnerRepository.save(runner));
    }

    @Override
    public RunnerResponseDTO updateStatus(Long id, String status) {
        Runner runner = runnerRepository.findById(id)
                .orElseThrow(()->new RunnerNotFoundException("Runner not fount with id:"+id));
        runner.setStatus(RunnerStatus.valueOf(status));
        return runnerMapper.toDTO(runnerRepository.save(runner));
    }

    @Override
    public RunnerResponseDTO updateAadharDocument(Long id, String aadharDocumentPath) {
        Runner runner = runnerRepository.findById(id)
                .orElseThrow(()->new RunnerNotFoundException("Runner not found with id:"+id));
        runner.setAadharDocumentPath(aadharDocumentPath);
        return runnerMapper.toDTO(runnerRepository.save(runner));
    }

    @Override
    public RunnerResponseDTO updateLicenseDocument(Long id, String licenseDocumentPath) {
        Runner runner = runnerRepository.findById(id)
                .orElseThrow(()->new RunnerNotFoundException("Runner not found with id:"+id));
        runner.setLicenseDocumentPath(licenseDocumentPath);
        return runnerMapper.toDTO(runnerRepository.save(runner));
    }



    @Override
    public void deleteRunner(Long id) {
        if (!runnerRepository.existsById(id)) {
            throw new RunnerNotFoundException("Runner not found with id: " + id);
        }
        runnerRepository.deleteById(id);
    }
}
