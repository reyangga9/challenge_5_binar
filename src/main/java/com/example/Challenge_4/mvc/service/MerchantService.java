package com.example.Challenge_4.mvc.service;

import com.example.Challenge_4.mvc.entity.Merchant;

import java.util.*;
import java.util.Map;

public interface MerchantService {
    Map save(Merchant merchant);

    Map update(Merchant merchant);

    Map delete(UUID id);

    Map getById(UUID id);

    List<Merchant> getAllMerchants();

    Map getMerchantAndProducts(UUID merchantId);
}
