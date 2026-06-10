package com.product_service.service;

import com.product_service.dto.ProductRequestDTO;
import com.product_service.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {

    ProductResponseDTO addProduct(ProductRequestDTO requestDTO);
    ProductResponseDTO getProductById(Long id);
    List<ProductResponseDTO> getAllProduct();
    List<ProductResponseDTO> getProductByFarmerId(Long farmerId);
    List<ProductResponseDTO> getAvailableProductsByCity(String city);
    List<ProductResponseDTO> getAvailableProductByDistrict(String district);
    List<ProductResponseDTO> getExpiredProductsByFarmerId(Long farmerId);
    ProductResponseDTO updateProduct(Long id , ProductRequestDTO requestDTO);
    ProductResponseDTO reduceStock(Long id , Double quantity);
    ProductResponseDTO restoreStock(Long id , Double quantity);
    void removeProduct(Long id);
    void markExpiredProduct();

}
