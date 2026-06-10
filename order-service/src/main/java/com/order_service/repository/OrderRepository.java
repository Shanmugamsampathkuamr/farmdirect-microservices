package com.order_service.repository;

import com.order_service.model.Order;
import com.order_service.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order>  findByUserId(Long userId);
    List<Order> findByFarmerId(Long farmerId);
    List<Order> findByRunnerId(Long runnerId);
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByUserIdAndStatus(Long userId , OrderStatus status);
    List<Order> findByFarmerIdAndStatus(Long farmerId , OrderStatus status);

}
