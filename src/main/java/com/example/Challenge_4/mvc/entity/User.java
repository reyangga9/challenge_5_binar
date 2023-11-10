package com.example.Challenge_4.mvc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "\"users\"")
public class User {
    @Id
    @GeneratedValue
    private UUID id = UUID.randomUUID(); // Generates a random UUID

    @NotNull
    @NotBlank
    private String username;

    @Column(unique = true)
    @NotNull
    @NotBlank
    private String email_address;

    @NotNull
    @NotBlank
    private String password;

}

