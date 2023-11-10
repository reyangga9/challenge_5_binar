package com.example.Challenge_4.mvc.service.impl;


import com.example.Challenge_4.Config;
import com.example.Challenge_4.mvc.entity.Order;
import com.example.Challenge_4.mvc.entity.User;
import com.example.Challenge_4.mvc.repository.OrderRepository;
import com.example.Challenge_4.mvc.repository.UserRepository;
import com.example.Challenge_4.mvc.service.OrderService;

import com.example.Challenge_4.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class OrderImpl implements OrderService {

    @Autowired
    public Response response;

    @Autowired
    public OrderRepository orderRepository;
    @Autowired
    public UserRepository userRepository;

    @Override
    public Map save(Order request) {
        try {
            log.info("Save order");



            if (request.getDestination_address() == null || request.getDestination_address().isEmpty()) {
                return response.Error(Config.DESTINATION_ADDRESS_REQUIRED);
            }

            if (request.getUser() == null || request.getUser().getId() == null) {
                return response.Error(Config.USER_REQUIRED);
            }

            Optional<User> checkUser = userRepository.findById(request.getUser().getId());

            if (checkUser.isEmpty()) {
                return response.Error(Config.USER_NOT_FOUND);
            }

            request.setUser(checkUser.get());

            return response.sukses(orderRepository.save(request));
        } catch (Exception e) {
            log.error("Save order error: " + e.getMessage());
            return response.Error("Save Order = " + e.getMessage());
        }
    }

    @Override
    public Map update(Order request) {
        try {
            log.info("Update order");

            if (request.getId() == null) {
                return response.Error(Config.ID_REQUIRED);
            }

            Optional<Order> checkOrder = orderRepository.findById(request.getId());

            if (checkOrder.isEmpty()) {
                return response.Error(Config.ORDER_NOT_FOUND);
            }


            checkOrder.get().setDestination_address(request.getDestination_address());
            checkOrder.get().setCompleted(request.isCompleted());

            return response.sukses(orderRepository.save(checkOrder.get()));
        } catch (Exception e) {
            log.error("Update order error: " + e.getMessage());
            return response.Error("Update Order = " + e.getMessage());
        }
    }

    @Override
    public Map delete(UUID orderId) {
        try {
            log.info("Delete order");

            if (orderId == null) {
                return response.Error(Config.ID_REQUIRED);
            }

            Optional<Order> checkOrder = orderRepository.findById(orderId);

            if (checkOrder.isEmpty()) {
                return response.Error(Config.ORDER_NOT_FOUND);
            }

            // Soft delete by setting the completed flag
            checkOrder.get().setCompleted(true);
            orderRepository.save(checkOrder.get());

            return response.sukses(Config.SUCCESS);
        } catch (Exception e) {
            log.error("Delete order error: " + e.getMessage());
            return response.Error("Delete Order = " + e.getMessage());
        }
    }

    @Override
    public Map getByID(UUID orderId) {
        try {
            log.info("Get order by ID");

            Optional<Order> checkOrder = orderRepository.findById(orderId);

            if (checkOrder.isEmpty()) {
                return response.Error(Config.ORDER_NOT_FOUND);
            }

            return response.sukses(checkOrder.get());
        } catch (Exception e) {
            log.error("Get order by ID error: " + e.getMessage());
            return response.Error("Get Order by ID = " + e.getMessage());
        }
    }

    @Override
    public List<Order> getAllOrders() {
        try {
            log.info("Get all orders");
            return orderRepository.findAll();
        } catch (Exception e) {
            log.error("Get all orders error: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
