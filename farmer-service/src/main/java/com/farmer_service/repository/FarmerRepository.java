package com.farmer_service.repository;

import com.farmer_service.model.Farmer;
import com.farmer_service.model.FarmerVerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FarmerRepository extends JpaRepository<Farmer , Long> {

    Optional<Farmer> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByAadharNumber(String aadharNumber);
    List<Farmer> findByVerificationStatus(FarmerVerificationStatus status);
    List<Farmer> findByCity(String city);
    List<Farmer> findByDistrict(String district);

}
