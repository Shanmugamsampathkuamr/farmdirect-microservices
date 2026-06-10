package com.product_service.scheduler;

import com.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductExpiryScheduler {

    private final ProductService productService;

    @Scheduled(cron = "0 0 0 * * *")
    public void checkAndMarkExpiredProduct(){
        log.info("Running expiry check at: {}", LocalDateTime.now());
        productService.markExpiredProduct();
        log.info("Expiry check completed at:{}",LocalDateTime.now());
    }
}
