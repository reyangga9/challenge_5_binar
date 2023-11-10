package com.example.Challenge_4.mvc.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id = UUID.randomUUID(); // Generates a random UUID


    private String product_name;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

}
