package com.example.Challenge_4.mvc.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "\"Order\"")

public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id = UUID.randomUUID(); // Generates a random UUID


    private Timestamp order_time = new Timestamp(System.currentTimeMillis()); // Set order_time to current date

    private String destination_address;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean completed = false;



}
