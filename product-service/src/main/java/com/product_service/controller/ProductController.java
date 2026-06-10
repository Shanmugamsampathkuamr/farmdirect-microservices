package com.product_service.controller;

import com.product_service.dto.ProductRequestDTO;
import com.product_service.dto.ProductResponseDTO;
import com.product_service.service.ProductService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/addProduct")
    public ResponseEntity<ProductResponseDTO> addProduct(@Valid @RequestBody ProductRequestDTO requestDTO){
        return  new ResponseEntity<>(productService.addProduct(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/getProductId/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id){
        return  ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/allProduct")
    public ResponseEntity<List<ProductResponseDTO>> getAllProduct(){
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @GetMapping("/farmer/{farmerId}")
    public ResponseEntity<List<ProductResponseDTO>> getProductByFarmer(@PathVariable Long farmerId){

        return ResponseEntity.ok(productService.getProductByFarmerId(farmerId));

    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<ProductResponseDTO>> getAvailableProductByCity(@PathVariable String city){
        return ResponseEntity.ok(productService.getAvailableProductsByCity(city));
    }

    @GetMapping("/district/{district}")
    public ResponseEntity<List<ProductResponseDTO>> getAvailableProductByDistrict(@PathVariable String district){
        return ResponseEntity.ok(productService.getAvailableProductByDistrict(district));
    }

    @GetMapping("/farmer/{farmerId}/expired")
    public ResponseEntity<List<ProductResponseDTO>> getExpiredProductByFarmer(@PathVariable Long farmerId){
        return ResponseEntity.ok(productService.getExpiredProductsByFarmerId(farmerId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponseDTO>updateProduct(@PathVariable Long id ,@RequestBody ProductRequestDTO requestDTO){
        return ResponseEntity.ok(productService.updateProduct(id ,requestDTO ));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> removeProduct(@PathVariable Long id){
        productService.removeProduct(id);
        return ResponseEntity.ok("ProductRemovedSuccessfully");
    }

    @PutMapping("/{id}/reduce-stock")
    public ResponseEntity<ProductResponseDTO> reduceStock(@PathVariable Long id , @RequestParam Double quantity){
        return ResponseEntity.ok(productService.reduceStock(id, quantity));
    }

    @PutMapping("/{id}/restore-stock")
    public ResponseEntity<ProductResponseDTO> restoreStock(@PathVariable Long id, @RequestParam Double quantity){
        return ResponseEntity.ok(productService.restoreStock(id,quantity));
    }
}
