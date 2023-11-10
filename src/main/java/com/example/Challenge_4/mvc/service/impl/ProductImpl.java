package com.example.Challenge_4.mvc.service.impl;

import com.example.Challenge_4.Config;
import com.example.Challenge_4.mvc.entity.Merchant;
import com.example.Challenge_4.mvc.entity.Order;
import com.example.Challenge_4.mvc.entity.Product;
import com.example.Challenge_4.mvc.repository.ProductRepository;
import com.example.Challenge_4.mvc.repository.MerchantRepository;
import com.example.Challenge_4.mvc.service.ProductService;

import com.example.Challenge_4.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class ProductImpl implements ProductService {
    @Override
    public Map getByID(UUID productId) {
        return null;
    }


    @Autowired
    public Response response;

    @Autowired
    public ProductRepository productRepository;

    @Autowired
    public MerchantRepository merchantRepository;

    @Override
    public Map save(Product request) {
        try {
            log.info("Save product");

            if (request.getProduct_name() == null || request.getProduct_name().isEmpty()) {
                return response.Error(Config.PRODUCT_NAME_REQUIRED);
            }

            if (request.getPrice() == null || request.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                return response.Error(Config.PRODUCT_PRICE_INVALID);
            }

            if (request.getMerchant() == null || request.getMerchant().getId() == null) {
                return response.Error(Config.MERCHANT_REQUIRED);
            }

            Optional<Merchant> checkMerchant = merchantRepository.findById(request.getMerchant().getId());

            if (checkMerchant.isEmpty()) {
                return response.Error(Config.MERCHANT_NOT_FOUND);
            }

            request.setMerchant(checkMerchant.get());

            return response.sukses(productRepository.save(request));
        } catch (Exception e) {
            log.error("Save product error: " + e.getMessage());
            return response.Error("Save Product = " + e.getMessage());
        }
    }

    @Override
    public Map update(Product request) {
        try {
            log.info("Update product");

            if (request.getId() == null) {
                return response.Error(Config.ID_REQUIRED);
            }

            Optional<Product> checkProduct = productRepository.findById(request.getId());

            if (checkProduct.isEmpty()) {
                return response.Error(Config.PRODUCT_NOT_FOUND);
            }


            checkProduct.get().setProduct_name(request.getProduct_name());
            checkProduct.get().setPrice(request.getPrice());

            return response.sukses(productRepository.save(checkProduct.get()));
        } catch (Exception e) {
            log.error("Update product error: " + e.getMessage());
            return response.Error("Update Product = " + e.getMessage());
        }
    }

    @Override
    public Map delete(UUID productId) {
        return null;
    }


    @Override
    public List<Product> getAllProducts() {
        try {
            log.info("Get all product");
            return productRepository.findAll();
        } catch (Exception e) {
            log.error("Get all product error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

}
