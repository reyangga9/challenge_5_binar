package com.example.Challenge_4.mvc.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.example.Challenge_4.mvc.entity.Product;

public interface ProductService {
    Map save(Product product);

    Map update(Product product);

    Map delete(UUID productId);

    Map getByID(UUID productId);

    List<Product> getAllProducts();
}
