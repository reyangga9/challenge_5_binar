package com.example.Challenge_4.mvc.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;
    private String username;
    private String email_address;

}
