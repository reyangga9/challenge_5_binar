package com.example.Challenge_4.mvc.service.impl;

import java.util.HashMap;

import java.util.*;

import com.example.Challenge_4.mvc.dto.OrderDTO;
import com.example.Challenge_4.mvc.entity.Order;
import com.example.Challenge_4.mvc.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.example.Challenge_4.mvc.entity.User;
import com.example.Challenge_4.mvc.repository.UserRepository;
import com.example.Challenge_4.mvc.service.UserService;

@Service
public class UserImpl implements UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;


    @Override


    public Map save(User user) {
        Map map = new HashMap();

        if (user.getEmail_address() == null || user.getEmail_address().isEmpty() ||
                user.getUsername() == null || user.getUsername().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty()) {
            map.put("message", "All fields (email, username, password) are required");
            map.put("success", "false");
        } else {
            try {
                // Attempt to save the user
                User saveData = userRepository.save(user);
                map.put("data", saveData);
                map.put("success", "true");
            } catch (DataIntegrityViolationException e) {
                // Catch an exception if the email already exists
                map.put("message", "Email already exists");
                map.put("success", "false");
            }
        }

        return map;
    }


    @Override
    public Map update(User user) {
        try {
            //        1. ngecek ke db base on param ID
//        2. baru update
//        3. jika tidak ditemukan : ditolak / return not found
            User checkData = userRepository.findById(user.getId()).orElse(null);
            Map map = new HashMap();
            if (checkData == null) {
                map.put("message", "data not found");
                return map;
            }
            checkData.setUsername(user.getUsername());
            checkData.setPassword(user.getPassword());
            checkData.setEmail_address(user.getEmail_address());

            User doUpdate = userRepository.save(checkData);
            map.put("data", doUpdate);

            return map;
        } catch (Exception e) {
            Map map = new HashMap();
            map.put("data", "Internal server error");
            return map;
        }
    }

    @Override
    public Map delete(UUID user) {
        return null;
    }

    @Override
    public Map getByID(UUID userId) {
        Map<String, Object> response = new HashMap<>();
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            response.put("message", "User not found");
            return response;
        }

        User user = userOptional.get();

        List<OrderDTO> orderDTOs = new ArrayList<>();
        List<Order> orders = orderRepository.findByUserId(userId);

        for (Order order : orders) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(order.getId());
            orderDTO.setOrderTime(order.getOrder_time().toLocalDateTime());
            orderDTO.setDestinationAddress(order.getDestination_address());
            orderDTO.setCompleted(order.isCompleted());

            orderDTOs.add(orderDTO);
        }

        response.put("user", user);
        response.put("orders", orderDTOs);

        return response;
    }



    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

