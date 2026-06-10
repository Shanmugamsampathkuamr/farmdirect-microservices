package com.order_service.controller;

import com.order_service.dto.OrderRequestDTO;
import com.order_service.dto.OrderResponseDTO;
import com.order_service.model.OrderStatus;
import com.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<OrderResponseDTO> placeOrder(@Valid @RequestBody OrderRequestDTO requestDTO){
        return  new ResponseEntity<>(orderService.placeOrder(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/getOrder/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrderByUser(@PathVariable Long userId){
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    @GetMapping("/farmer/{id}")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrdersByFarmer(@PathVariable Long farmerId){
        return ResponseEntity.ok(orderService.getAllOrdersByFarmerId(farmerId));

    }

    @GetMapping("/runner/{id}")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrderByRunner(@PathVariable Long runnerId){
        return ResponseEntity.ok(orderService.getOrdersByRunnerId(runnerId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrderByStatus(@PathVariable OrderStatus status){
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }

    @PutMapping("/order/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(@PathVariable Long id , @RequestParam String status){
        return ResponseEntity.ok(orderService.updateOrderStatus(id , status));

    }

    @PutMapping("/order/{id}/assing-runner/{runnerId}")
    public ResponseEntity<OrderResponseDTO> assignRunner(@PathVariable Long orderId , @PathVariable Long RunnerId){
        return ResponseEntity.ok(orderService.assigningRunner(orderId,RunnerId));
    }

    @PutMapping("/orderid/{id}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId){
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("Order canceled successfully");
    }
}
