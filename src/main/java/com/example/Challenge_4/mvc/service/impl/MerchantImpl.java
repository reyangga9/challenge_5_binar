package com.example.Challenge_4.mvc.service.impl;

import com.example.Challenge_4.mvc.entity.Merchant;
import com.example.Challenge_4.mvc.entity.Product;
import com.example.Challenge_4.mvc.repository.MerchantRepository;
import com.example.Challenge_4.mvc.repository.ProductRepository;
import com.example.Challenge_4.mvc.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MerchantImpl implements MerchantService {

    @Autowired
    public MerchantRepository merchantRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Map save(Merchant merchant) {
        Map map = new HashMap();

        if (merchant.getMerchant_name() == null || merchant.getMerchant_name().isEmpty() ||
                merchant.getMerchant_location() == null || merchant.getMerchant_location().isEmpty()) {
            map.put("message", "All fields (merchant_name, merchant_location, open) are required");
            map.put("success", "false");
        } else {
            try {
                // Attempt to save the user
                Merchant saveData = merchantRepository.save(merchant);
                map.put("data", saveData);
                map.put("success", "true");
            } catch (DataIntegrityViolationException e) {
                // Catch an exception if the email already exists
                map.put("message", "Email already exists");
                map.put("success", "false");
            }
        }

        return map;
    }

    public Map update(Merchant merchant) {
        Map response = new HashMap();
        try {
            Merchant checkData = merchantRepository.findById(merchant.getId()).orElse(null);

            if (checkData != null) {
                checkData.setMerchant_name(merchant.getMerchant_name());
                checkData.setMerchant_location(merchant.getMerchant_location());
                Merchant updatedMerchant = merchantRepository.save(merchant);
                response.put("success", true);
                response.put("data", updatedMerchant);
            } else {
                response.put("success", false);
                response.put("message", "Merchant not found");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }

    @Override
    public Map delete(UUID id) {
        Map response = new HashMap();
        try {
            if (merchantRepository.existsById(id)) {
                merchantRepository.deleteById(id);
                response.put("success", true);
                response.put("message", "Merchant deleted");
            } else {
                response.put("success", false);
                response.put("message", "Merchant not found");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }

    @Override
    public Map getById(UUID id) {
        Map response = new HashMap();
        try {
            if (merchantRepository.existsById(id)) {
                Merchant merchant = merchantRepository.findById(id).get();
                response.put("success", true);
                response.put("data", merchant);
            } else {
                response.put("success", false);
                response.put("message", "Merchant not found");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }

    @Override
    public List<Merchant> getAllMerchants() {
        return merchantRepository.findAll();
    }

    @Override
    public Map getMerchantAndProducts(UUID merchantId) {
        Map<String, Object> response = new HashMap<>();

        // Get the merchant by ID
        Optional<Merchant> merchantOptional = merchantRepository.findById(merchantId);

        if (merchantOptional.isPresent()) {
            Merchant merchant = merchantOptional.get();
            response.put("merchant", merchant);

            // Get the products associated with the merchant
            List<Product> products = productRepository.findByMerchant_Id(merchantId);
            response.put("products", products);

            return response;
        } else {
            response.put("message", "Merchant not found");
            return response;
        }
    }
    }
