package com.runner_service.repository;

import com.runner_service.model.Runner;
import com.runner_service.model.RunnerStatus;
import com.runner_service.model.RunnerVerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RunnerRepository extends JpaRepository<Runner , Long> {

    Optional<Runner> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByAadharNumber(String aadharNumber);
    boolean existsByLicenseNumber(String licenseNumber);
    List<Runner> findByVerificationStatus(RunnerVerificationStatus status);
    List<Runner> findByCity(String city);
    List<Runner> findByStatus(RunnerStatus status);

}
