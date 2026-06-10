package com.order_service.service;

import com.order_service.dto.*;
import com.order_service.exception.InsufficientStockException;
import com.order_service.exception.OrderNotFoundException;
import com.order_service.feginclient.NotificationFeignClient;
import com.order_service.feginclient.ProductFeignClient;
import com.order_service.feginclient.RunnerFeignClient;
import com.order_service.kafka.NotificationEvent;
import com.order_service.kafka.NotificationProducer;
import com.order_service.mapper.OrderMapper;
import com.order_service.model.Order;
import com.order_service.model.OrderStatus;
import com.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private  final OrderMapper orderMapper;
    private final ProductFeignClient productFeignClient;
    private final RunnerFeignClient runnerFeignClient;
    private final NotificationProducer notificationProducer;


    // place order //



    @Override
    public OrderResponseDTO placeOrder(OrderRequestDTO requestDTO) {
        ProductResponseDTO product = productFeignClient.getProductById(requestDTO.getProductId());

        if (!product.getStatus().equals("AVAILABLE")){
            throw new InsufficientStockException("Product is nor available");
        }
        if (product.getQuantityInKg()<requestDTO.getOrderedQuantityInKg()){
            throw new InsufficientStockException("Only " +product.getQuantityInKg()+"kg available");
        }

        productFeignClient.reduceStock(requestDTO.getProductId(),requestDTO.getOrderedQuantityInKg());

        Order order = orderMapper.toEntity(requestDTO , product);
        Order savedOrder = orderRepository.save(order);

        try{
            notificationProducer.sendNotification(
                    NotificationEvent.builder()
                            .receiverId(savedOrder.getId())
                            .receiverType("FARMER")
                            .title("New Order Received!")
                            .message("You have a new order for"
                                    +savedOrder.getCropName()
                                    +"-"
                                    +savedOrder.getOrderQuantityInKg()
                                    + "kg has been placed successfully!")
                            .notificationType("ORDER_PLACED")
                            .referenceId(savedOrder.getId())
                            .build()
            );

        } catch (Exception e) {
            log.error("Notification failed for order placed:{}",e.getMessage());
        }

        try {
            notificationProducer.sendNotification(
                    NotificationEvent.builder()
                            .receiverId(savedOrder.getUserId())
                            .receiverType("USER")
                            .title("Order Placed Successfully")
                            .message("Your order for"
                                    + savedOrder.getCropName()
                                    +"-"
                                    +savedOrder.getOrderQuantityInKg()
                                    +"kg has been placed successfully"

                            )
                            .notificationType("ORDER_PLACED")
                            .referenceId(savedOrder.getId())
                            .build()
            );
        }catch (Exception e){
            log.error("Notification failed for user order placed:{}",e.getMessage());
        }

        return orderMapper.toDTO(savedOrder);
    }

    // cancel order //

    @Override
    public void cancelOrder(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(()->new OrderNotFoundException("Order not found with id:"+id));
        if (order.getStatus()!=OrderStatus.CANCELLED && order.getStatus() != OrderStatus.DELIVERED){
            productFeignClient.reduceStock(order.getProductId(),order.getOrderQuantityInKg());
        }
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        try {
            notificationProducer.sendNotification(
                    NotificationEvent.builder()
                            .receiverId(order.getUserId())
                            .receiverType("FARMER")
                            .title("Order Cancelled")
                            .message("Order for"
                                    +order.getCropName()
                                    +"-"
                                    +order.getOrderQuantityInKg()
                            )
                            .referenceId(order.getId())
                            .build()
            );
        } catch (Exception e) {
            log.error("Notification not sent to the farmer :{}",e.getMessage());
        }
    }

     // update order status //


    @Override
    public OrderResponseDTO updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(
                        "Order not found with id: " + id));
        order.setStatus(OrderStatus.valueOf(status));
        Order updatedOrder = orderRepository.save(order);

        // ✅ When delivered update runner back to ACTIVE
        if (status.equals("DELIVERED")) {
            if (updatedOrder.getRunnerId() != null) {
                try {
                    runnerFeignClient.updateRunnerStatus(
                            updatedOrder.getRunnerId(), "ACTIVE");
                    log.info("Runner {} status updated to ACTIVE",
                            updatedOrder.getRunnerId());
                } catch (Exception e) {
                    log.error("Runner status update failed: {}", e.getMessage());
                }
            }

            // Notify user
            try {
                notificationProducer.sendNotification(
                        NotificationEvent.builder()
                                .receiverId(updatedOrder.getUserId())
                                .receiverType("USER")
                                .title("Order Delivered!")
                                .message("Your order for "
                                        + updatedOrder.getCropName()
                                        + " delivered successfully!")
                                .notificationType("ORDER_DELIVERED")
                                .referenceId(updatedOrder.getId())
                                .build()
                );
            } catch (Exception e) {
                log.error("Notification failed: {}", e.getMessage());
            }

            // Notify farmer
            try {
                notificationProducer.sendNotification(
                        NotificationEvent.builder()
                                .receiverId(updatedOrder.getFarmerId())
                                .receiverType("FARMER")
                                .title("Crop Delivered!")
                                .message("Your crop "
                                        + updatedOrder.getCropName()
                                        + " delivered to customer!")
                                .notificationType("ORDER_DELIVERED")
                                .referenceId(updatedOrder.getId())
                                .build()
                );
            } catch (Exception e) {
                log.error("Notification failed: {}", e.getMessage());
            }

        } else if (status.equals("CONFIRMED")) {
            try {
                notificationProducer.sendNotification(
                        NotificationEvent.builder()
                                .receiverId(updatedOrder.getUserId())
                                .receiverType("USER")
                                .title("Order Confirmed!")
                                .message("Your order for "
                                        + updatedOrder.getCropName()
                                        + " confirmed by farmer!")
                                .notificationType("ORDER_CONFIRMED")
                                .referenceId(updatedOrder.getId())
                                .build()
                );
            } catch (Exception e) {
                log.error("Notification failed: {}", e.getMessage());
            }

        } else if (status.equals("PICKED_UP")) {
            try {
                notificationProducer.sendNotification(
                        NotificationEvent.builder()
                                .receiverId(updatedOrder.getUserId())
                                .receiverType("USER")
                                .title("Order Picked Up!")
                                .message("Your order for "
                                        + updatedOrder.getCropName()
                                        + " picked up by runner!")
                                .notificationType("ORDER_PICKED_UP")
                                .referenceId(updatedOrder.getId())
                                .build()
                );
            } catch (Exception e) {
                log.error("Notification failed: {}", e.getMessage());
            }
        }

        return orderMapper.toDTO(updatedOrder);
    }

    // assigningRunner //


    @Override
    public OrderResponseDTO assigningRunner(Long orderId, Long runnerId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new OrderNotFoundException("order not found with id:"+orderId));
        order.setRunnerId(runnerId);
        order.setStatus(OrderStatus.ASSIGNED);
        Order updatedOrder = orderRepository.save(order);
        try {
            runnerFeignClient.updateRunnerStatus(runnerId,"ON_DELIVERY");
            log.info("Runner {} status update to ON_DELIVERY",runnerId);
        }catch (Exception e){
            log.error("Runner status update failed:{}",e.getMessage());
        }

        try{
            notificationProducer.sendNotification(
                    NotificationEvent.builder()
                            .receiverId(updatedOrder.getUserId())
                            .receiverType("RUNNER")
                            .title("New Delivery Assigned")
                            .message("you have a new delivery for"
                                    + updatedOrder.getCropName()
                                    +"-"
                                    +updatedOrder.getOrderQuantityInKg()
                                    +"kg")
                            .notificationType("ORDER_ASSIGNED")
                            .build()
            );
        } catch (Exception e) {
            log.error("Notification failed for runner assignment:{}",e.getMessage());
        }
        return orderMapper.toDTO(order);
    }



    // get Order by Id

    @Override
    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(()->new OrderNotFoundException("Order not found with id:"+id));
        return orderMapper.toDTO(order);
    }


    // get all orders

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    //  get Order By User Id //


    @Override
    public List<OrderResponseDTO> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    // GetAll Order By FarmerId

    @Override
    public List<OrderResponseDTO> getAllOrdersByFarmerId(Long farmerId) {
        return orderRepository.findByFarmerId(farmerId)
                .stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());

    }

    // Get orders By Runner

    @Override
    public List<OrderResponseDTO> getOrdersByRunnerId(Long runnerId) {
        return orderRepository.findByRunnerId(runnerId)
                .stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());

    }

    // get Order By Status


    @Override
    public List<OrderResponseDTO> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status)
                .stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }


}
