package com.example.Challenge_4.mvc.controller;

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

    @GetMapping(value = {"/{id}", "/{id}/"})
    public ResponseEntity<Map> getById(@PathVariable("id") UUID id) {
        return new ResponseEntity<Map>(user.getByID(id), HttpStatus.OK);
    }


    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User> users = user.getAllUsers();
        return users;
    }
}