package com.product_service.service;

import com.product_service.dto.NotificationRequestDTO;
import com.product_service.dto.ProductRequestDTO;
import com.product_service.dto.ProductResponseDTO;
import com.product_service.exception.ProductNotFoundException;
import com.product_service.feignclient.NotificationFeignClient;
import com.product_service.kafka.NotificationEvent;
import com.product_service.kafka.NotificationProducer;
import com.product_service.mapper.ProductMapper;
import com.product_service.model.Product;
import com.product_service.model.ProductStatus;
import com.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private  final ProductMapper productMapper;
    private final NotificationProducer notificationProducer;



    @Override
    public ProductResponseDTO addProduct(ProductRequestDTO requestDTO) {
        Product product = productMapper.toEntity(requestDTO);
        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
       Product product = productRepository.findById(id)
               .orElseThrow(()->new ProductNotFoundException("Product Not Found with id:"+id));
       return productMapper.toDTO(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProduct()
    {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());

    }

    @Override
    public List<ProductResponseDTO> getProductByFarmerId(Long farmerId) {
        return productRepository.findByFarmerId(farmerId)
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> getAvailableProductsByCity(String city) {
        return productRepository.findByCityAndStatus(city , ProductStatus.AVAILABLE)
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> getAvailableProductByDistrict(String district) {
        return productRepository.findByDistrictAndStatus(district ,ProductStatus.AVAILABLE)
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> getExpiredProductsByFarmerId(Long farmerId) {
        return productRepository.findByFarmerIdAndStatus(farmerId ,ProductStatus.EXPIRED)
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO requestDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("product Not Found with id:"+id));
        product.setCropName(requestDTO.getCropName());
        product.setDescription(requestDTO.getDescription());
        product.setQuantityInKg(requestDTO.getQuantityInKg());
        product.setCity(requestDTO.getCity());
        product.setDistrict(requestDTO.getDistrict());
        product.setVillage(requestDTO.getVillage());
        product.setHarvestDate(requestDTO.getHarvestDate());
        product.setExpiryDate(requestDTO.getHarvestDate());
        product.setStorableDays(requestDTO.getStorableDays());
        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductResponseDTO reduceStock(Long id, Double quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("Product not found with id:"+id));
        if (product.getQuantityInKg()<quantity){
            throw new RuntimeException("Insufficient stock for product:"+id);

        }
        product .setQuantityInKg(product.getQuantityInKg()-quantity);
        if (product.getQuantityInKg()==0){
            product.setStatus(ProductStatus.OUT_OF_STOCK);

            try{
                notificationProducer.sendNotification(
                        NotificationEvent.builder()
                                .receiverId(product.getId())
                                .receiverType("FARMER")
                                .title("Product out Of stock!")
                                .message("your crop"
                                        +product.getCropName()
                                        +"is  now out of stock!"
                                )
                                .notificationType("PRODUCT_EXPIRED")
                                .referenceId(product.getId())
                                .build()
                );

            } catch (Exception e) {
                log.error("Message not send for former:{}",e.getMessage());
            }
        }
        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductResponseDTO restoreStock(Long id, Double quantity) {
         Product product = productRepository.findById(id)
                 .orElseThrow(()->new ProductNotFoundException("product nor found with id:"+id));
         product.setQuantityInKg(product.getQuantityInKg()+quantity);
         if (product.getStatus() == ProductStatus.OUT_OF_STOCK){
             product.setStatus(ProductStatus.AVAILABLE);
         }
         return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public void removeProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("product not found id:"+id));
        product.setStatus(ProductStatus.REMOVED);
        productRepository.save(product);

    }

    @Override
    public void markExpiredProduct() {

        List<Product> expiredProducts = productRepository.findByExpiryDateBeforeAndStatus(LocalDate.now(), ProductStatus.AVAILABLE);
        if (!expiredProducts.isEmpty()){
            expiredProducts.forEach(product ->{ product.setStatus(ProductStatus.EXPIRED);

            try {
                notificationProducer.sendNotification(
                        NotificationEvent.builder()
                                .receiverId(product.getFarmerId())
                                .receiverType("FARMER")
                                .title("Product Expired!")
                                .message("your crop listing"
                                        +product.getCropName()
                                        +"has expired on"
                                        +product.getExpiryDate()
                                        +"and has been removed from market!"

                                )
                                .notificationType("PRODUCT_EXPIRED")
                                .referenceId(product.getId())
                                .build()
                );
            }catch (Exception e){
                log.error("Notification failed for expired product:{}",e.getMessage());
            }
                    });

            productRepository.saveAll(expiredProducts);
            log.info("Marked{} product as EXPIRED",expiredProducts.size());
        }


    }
}
