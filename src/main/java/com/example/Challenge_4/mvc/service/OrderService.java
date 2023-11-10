package com.example.Challenge_4.mvc.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.example.Challenge_4.mvc.entity.Order;

public interface OrderService {
    Map save(Order order);

    Map update(Order order);

    Map delete(UUID orderId);

    Map getByID(UUID orderId);

    List<Order> getAllOrders();
}
