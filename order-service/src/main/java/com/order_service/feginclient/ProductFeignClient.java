package com.order_service.feginclient;

import com.order_service.dto.ProductResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service")
public interface ProductFeignClient {

    @GetMapping("/api/products/getProductId/{id}")
    ProductResponseDTO getProductById(@PathVariable Long id);

    @PutMapping("/api/products/{id}/reduce-stock")
    ProductResponseDTO reduceStock(@PathVariable Long id , @RequestParam Double quantity);

    @PutMapping("/api/products/{id}/restore-stock")
    ProductResponseDTO restoreStock(@PathVariable Long id , @RequestParam Double quantity);
}
