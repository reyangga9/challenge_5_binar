package com.example.Challenge_4.mvc.service;

import java.util.*;
import java.util.Map;

import com.example.Challenge_4.mvc.entity.User;

public interface UserService {
    Map save(User user);

    Map update(User user);

    Map delete(UUID userId);

    Map getByID(UUID user);

    List<User> getAllUsers();

}
