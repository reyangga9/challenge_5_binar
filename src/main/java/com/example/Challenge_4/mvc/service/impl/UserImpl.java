package com.example.Challenge_4.mvc.service.impl;

import java.util.HashMap;

import java.util.*;

import com.example.Challenge_4.mvc.dto.OrderDTO;
import com.example.Challenge_4.mvc.dto.UserDTO;
import com.example.Challenge_4.mvc.entity.Order;
import com.example.Challenge_4.mvc.repository.OrderRepository;
import com.example.Challenge_4.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.Challenge_4.mvc.entity.User;
import com.example.Challenge_4.mvc.repository.UserRepository;
import com.example.Challenge_4.mvc.service.UserService;

@Service
public class UserImpl implements UserService {

    @Autowired
    public Response response;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;


    @Override


    public Map<String, Object> save(User user) {
        Map<String, Object> response = new HashMap<>();

        if (user.getEmail_address() == null || user.getEmail_address().isEmpty() ||
                user.getUsername() == null || user.getUsername().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty()) {
            response.put("message", "All fields (email, username, password) are required");
            response.put("success", false);
        } else {
            try {
                // Attempt to save the user
                User saveData = userRepository.save(user);
                Map<String, Object> userDTO = new HashMap<>();
                userDTO.put("id", saveData.getId());
                userDTO.put("username", saveData.getUsername());
                userDTO.put("email_address", saveData.getEmail_address());

                response.put("data", userDTO);
                response.put("success", true);
            } catch (DataIntegrityViolationException e) {
                // Catch an exception if the email already exists
                response.put("message", "Email already exists");
                response.put("success", false);
            }
        }

        return response;
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
    public Map getByID(UUID userId, int page, int size) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<User> userOptional = userRepository.findById(userId);

            if (userOptional.isEmpty()) {
                response.put("message", "User not found");
                return response;
            }

            User user = userOptional.get();

            // Create Pageable object for pagination
            Pageable pageable = PageRequest.of(page, size);

            // Fetch orders using pagination
            Page<Order> orderPage = orderRepository.findByUserId(userId, pageable);

            // Convert Order entities to DTOs
            List<OrderDTO> orderDTOs = orderPage.getContent().stream()
                    .map(order -> {
                        OrderDTO orderDTO = new OrderDTO();
                        orderDTO.setId(order.getId());
                        orderDTO.setOrder_time(order.getOrder_time().toLocalDateTime());
                        orderDTO.setDestination_address(order.getDestination_address());
                        orderDTO.setCompleted(order.isCompleted());
                        return orderDTO;
                    })
                    .collect(Collectors.toList());

            // Add user and paginated order information to the response
            response.put("user", user);
            response.put("orders", orderDTOs);
            response.put("current_page", orderPage.getNumber());
            response.put("total_items", orderPage.getTotalElements());
            response.put("total_pages", orderPage.getTotalPages());

        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
            response.put("error", "An error occurred while fetching user details");
        }

        return response;
    }



    @Override
    public Map getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            List<UserDTO> userDTOs = new ArrayList<>();

            for (User user : users) {
                UserDTO userDTO = new UserDTO();
                userDTO.setId(user.getId());
                userDTO.setUsername(user.getUsername());
                userDTO.setEmail_address(user.getEmail_address());


                userDTOs.add(userDTO);
            }


            return response.sukses(userDTOs);
        } catch (Exception e) {
            return response.error(e,402);
        }
    }
}

