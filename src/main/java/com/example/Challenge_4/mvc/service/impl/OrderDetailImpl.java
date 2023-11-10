package com.example.Challenge_4.mvc.service.impl;

import com.example.Challenge_4.Config;
import com.example.Challenge_4.mvc.entity.Order;
import com.example.Challenge_4.mvc.entity.OrderDetail;
import com.example.Challenge_4.mvc.entity.Product;
import com.example.Challenge_4.mvc.entity.User;
import com.example.Challenge_4.mvc.repository.OrderDetailRepository;
import com.example.Challenge_4.mvc.repository.OrderRepository;
import com.example.Challenge_4.mvc.repository.ProductRepository;
import com.example.Challenge_4.mvc.repository.UserRepository;
import com.example.Challenge_4.mvc.service.OrderDetailService;
import com.example.Challenge_4.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class OrderDetailImpl implements OrderDetailService {


    @Autowired
    public Response response;

    @Autowired
    public OrderDetailRepository orderDetailRepository;


    @Autowired
  public ProductRepository productRepository;

    @Autowired
    public OrderRepository orderRepository;

    @Override
    public Map save(OrderDetail orderDetail) {
        try {
            log.info("Save orderDetail");


            // Check if the orderDetail or its associated order is null
            if (orderDetail.getQuantity() <= 0) {
                return response.Error(Config.QUANTITY_REQURIRED);
            }

            // Check if order is provided
            if (orderDetail.getOrder() == null || orderDetail.getOrder().getId() == null) {
                return response.Error(Config.ORDER_ID_REQUIRED);
            }
//            Melakukan pengecekkan Order
            Optional<Order> checkOrder = orderRepository.findById(orderDetail.getOrder().getId());

            if (checkOrder.isEmpty()) {
                return response.Error(Config.ORDER_NOT_FOUND);
            }

            // Check if product_id is provided
            if (orderDetail.getProduct() == null || orderDetail.getProduct().getId() == null) {
                return response.Error(Config.PRODUCT_ID_REQURIRED);
            }

            //            Melakukan pengecekkan product
            Optional<Product> checkProduct = productRepository.findById(orderDetail.getProduct().getId());

            if (checkProduct.isEmpty()) {
                return response.Error(Config.PRODUCT_NOT_FOUND);
            }



            // Save the order detail
            OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);

            return response.sukses(savedOrderDetail);
        } catch (Exception e) {
            log.error("Save order error: " + e.getMessage());
            return response.Error("Save Order = " + e.getMessage());
        }
    }


    @Override
    public Map update(OrderDetail orderDetail) {
        return null;
    }

    @Override
    public Map delete(UUID id) {
        return null;
    }

    @Override
    public Map getById(UUID id) {
        return null;
    }

    @Override
    public Map getByUserId(UUID id) {
        Map map = new HashMap();
        Optional<Order> getBaseOptional = orderRepository.findById(id);
        if (getBaseOptional.isEmpty()) {
            map.put("message", "data not found");
            return map;
        }
        map.put("data", getBaseOptional.get());

        return map;
    }

    @Override
    public List<OrderDetail> getAllOrderDetails() {
        return null;
    }

    @Override
    public List<OrderDetail> getOrderDetailsByUser(UUID userId) {
        return orderDetailRepository.findByOrder_User_Id(userId);
    }
}
