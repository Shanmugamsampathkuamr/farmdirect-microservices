package com.product_service.mapper;

import com.product_service.dto.ProductRequestDTO;
import com.product_service.dto.ProductResponseDTO;
import com.product_service.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDTO dto){

        return Product.builder()
                .farmerId(dto.getFarmerId())
                .cropName(dto.getCropName())
                .description(dto.getDescription())
                .quantityInKg(dto.getQuantityInKg())
                .city(dto.getCity())
                .district(dto.getDistrict())
                .village(dto.getVillage())
                .harvestDate(dto.getHarvestDate())
                .storableDays(dto.getStorableDays())
                .build();

    }

    public ProductResponseDTO toDTO(Product product){
        return ProductResponseDTO.builder()
                .id(product.getId())
                .farmerId(product.getFarmerId())
                .cropName(product.getCropName())
                .description(product.getDescription())
                .quantityInKg(product.getQuantityInKg())
                .city(product.getCity())
                .district(product.getDistrict())
                .village(product.getVillage())
                .photoPath(product.getPhotoPath())
                .videoPath(product.getVideoPath())
                .harvestDate(product.getHarvestDate())
                .storableDate(product.getStorableDays())
                .expiryDate(product.getExpiryDate())
                .status(product.getStatus())
                .createdAt(product.getCreatedAt())
                .build();
    }
}
