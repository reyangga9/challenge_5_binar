package com.example.Challenge_4.mvc.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OrderDTO {
    private UUID id;
    private LocalDateTime orderTime;
    private String destinationAddress;
    private boolean completed;
}
