package com.product_service.repository;

import com.product_service.model.Product;
import com.product_service.model.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product ,Long> {

    List<Product> findByFarmerId(Long farmerId);
    List<Product> findByCity(String city);
    List<Product> findByDistrict(String district);
    List<Product> findByStatus(ProductStatus status);
    List<Product> findByCityAndStatus(String city , ProductStatus status);
    List<Product>  findByDistrictAndStatus(String district , ProductStatus status);
    List<Product> findByFarmerIdAndStatus(Long farmerId , ProductStatus status);
    List<Product> findByExpiryDateBeforeAndStatus(LocalDate date , ProductStatus status);


}
