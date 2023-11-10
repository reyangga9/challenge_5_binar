package com.example.Challenge_4.mvc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "merchant")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id = UUID.randomUUID();

    @NotNull
    @NotBlank
    private String merchant_name;

    @NotNull
    @NotBlank
    private String merchant_location;




    private boolean open;

    // Constructors, getters, and setters
}
