package com.example.Challenge_4.mvc.controller;

import com.example.Challenge_4.mvc.dto.UserDTO;
import com.example.Challenge_4.mvc.entity.User;
import com.example.Challenge_4.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    public UserService user;



    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Map> save(@RequestBody User request) {

        return new ResponseEntity<Map>(user.save(request), HttpStatus.OK);
    }

    @PutMapping(value = {"/update", "/update/"})
    public ResponseEntity<Map> update(@RequestBody User request) {
        return new ResponseEntity<Map>(user.update(request), HttpStatus.OK);
    }

//    @DeleteMapping(value = {"/delete", "/delete/"})
//    public ResponseEntity<Map> delete(@RequestBody User request) {
//        return new ResponseEntity<Map>(user.delete(request.getId()), HttpStatus.OK);
//    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getUserDetails(
            @RequestParam(required = true) UUID userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)

    {

        Map<String, Object> response = user.getByID(userId, page, size);

        if (response.containsKey("error")) {
            // If there's an error, return an error response
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (response.containsKey("message")) {
            // If there's a message, return a not found response
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            // If successful, return a success response
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }


    @GetMapping("/users")
    public ResponseEntity<Map> getAllUsers() {
        return new ResponseEntity<Map>(user.getAllUsers(),HttpStatus.OK);

    }
}