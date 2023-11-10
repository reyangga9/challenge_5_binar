package com.example.Challenge_4.mvc.service;

import com.example.Challenge_4.mvc.entity.OrderDetail;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OrderDetailService {
    Map save(OrderDetail orderDetail);

    Map update(OrderDetail orderDetail);

    Map delete(UUID id);

    Map getById(UUID id);

    Map getByUserId(UUID id);

    List<OrderDetail> getAllOrderDetails();

    List<OrderDetail> getOrderDetailsByUser(UUID userId);
}
