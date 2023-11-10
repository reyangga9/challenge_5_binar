package com.example.Challenge_4.mvc.controller;

import com.example.Challenge_4.mvc.entity.OrderDetail;
import com.example.Challenge_4.mvc.entity.User;
import com.example.Challenge_4.mvc.service.OrderDetailService;
import com.example.Challenge_4.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/orderDetail")
public class OrderDetailController {
    @Autowired
    public OrderDetailService orderDetailService;

    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Map> save(@RequestBody OrderDetail request) {

        return new ResponseEntity<Map>(orderDetailService.save(request), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDetail>> getOrderDetailsByUser(@PathVariable UUID userId) {
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByUser(userId);
        return ResponseEntity.ok(orderDetails);
    }


//    @GetMapping("/users")
//    public List<User> getAllUsers() {
//        List<User> users = user.getAllUsers();
//        return users;
//    }
}