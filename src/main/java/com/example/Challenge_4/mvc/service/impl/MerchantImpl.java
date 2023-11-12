package com.example.Challenge_4.mvc.service.impl;

import com.example.Challenge_4.mvc.entity.Merchant;
import com.example.Challenge_4.mvc.entity.Product;
import com.example.Challenge_4.mvc.repository.MerchantRepository;
import com.example.Challenge_4.mvc.repository.ProductRepository;
import com.example.Challenge_4.mvc.service.MerchantService;
import com.example.Challenge_4.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MerchantImpl implements MerchantService {

    @Autowired
    public Response response;

    @Autowired
    public MerchantRepository merchantRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Map save(Merchant merchant) {


        try {
            if (merchant.getMerchant_name() == null || merchant.getMerchant_name().isEmpty() ||
                    merchant.getMerchant_location() == null || merchant.getMerchant_location().isEmpty()) {
                return response.error("All fields (merchant_name, merchant_location, open) are required", 404);
            } else {

                // Attempt to save the user
                Merchant saveData = merchantRepository.save(merchant);
                return response.sukses(saveData);


            }
        }
        catch(Exception e){
return response.error(e,400);
            }

        }


    public Map update(Merchant merchant) {

        try {
            UUID id = merchant.getId();

            if (id == null) {
                return response.error("Merchant id cannot be null", 400);
            }

            Merchant checkData = merchantRepository.findById(merchant.getId()).orElse(null);

            if (checkData != null) {
                checkData.setMerchant_name(merchant.getMerchant_name());
                checkData.setMerchant_location(merchant.getMerchant_location());
                Merchant updatedMerchant = merchantRepository.save(merchant);
              return response.sukses(updatedMerchant);
            } else {
             return response.error("Merchant not found",404);
            }
        } catch (Exception e) {
            return response.error(e,400);
        }

    }

    @Override
    public Map delete(UUID id) {
        Map response = new HashMap();
        try {
            if (merchantRepository.existsById(id)) {
                merchantRepository.deleteById(id);
                response.put("success", true);
                response.put("message", "Merchant deleted");
                response.put("status",200);

            } else {
                response.put("success", false);
                response.put("message", "Merchant not found");
                response.put("status",404);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            response.put("status",400);
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
                response.put("status",200);
            } else {
                response.put("success", false);
                response.put("message", "Merchant not found");
                response.put("status",404);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            response.put("status",400);
        }
        return response;
    }



    @Override
    public List<Merchant> getAllMerchants() {
        return merchantRepository.findAll();
    }

    @Override
    public Map getMerchantAndProducts(UUID merchantId) {
     try{
         Map<String, Object> abc = new HashMap<>();

         // Get the merchant by ID
         Optional<Merchant> merchantOptional = merchantRepository.findById(merchantId);

         if (merchantOptional.isPresent()) {
             Merchant merchant = merchantOptional.get();
             abc.put("merchant", merchant);

             // Get the products associated with the merchant
             List<Product> products = productRepository.findByMerchant_Id(merchantId);
             abc.put("products", products);

             return response.sukses(abc) ;
         } else {
             abc.put("message", "Merchant not found");
             return response.error(abc,400);
         }
     }catch (Exception e){
         return response.error(e,400);
     }
    }
    }
