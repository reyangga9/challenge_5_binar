package com.example.Challenge_4.mvc.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OrderDTO {
    private UUID id;
    private LocalDateTime order_time;
    private String destination_address;
    private boolean completed;
}
