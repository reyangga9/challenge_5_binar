package com.example.Challenge_4.mvc.controller;

import com.example.Challenge_4.mvc.entity.Order;
import com.example.Challenge_4.mvc.repository.OrderRepository;
import com.example.Challenge_4.mvc.service.OrderService;
import com.example.Challenge_4.mvc.utils.SimpleStringUtils;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {
    SimpleStringUtils simpleStringUtils = new SimpleStringUtils();




    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Map> save(@RequestBody Order request) {
        return new ResponseEntity<Map>(orderService.save(request), HttpStatus.OK);
    }

    @PutMapping(value = {"/update", "/update/"})
    public ResponseEntity<Map> update(@RequestBody Order request) {
        return new ResponseEntity<Map>(orderService.update(request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteOrder(@PathVariable UUID id) {
        Map<String, Object> response = orderService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getOrderById(@PathVariable UUID id) {
        Map<String, Object> response = orderService.getByID(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = {"/list-spec", "/list-spec/"})
    public ResponseEntity<Page<Order>> listSpec(
            @RequestParam() Integer page,
            @RequestParam(required = true) Integer size,
            @RequestParam(required = false) Boolean completed,
            @RequestParam(required = false) String orderby,
            @RequestParam(required = false) String ordertype) {

        try {
            Pageable show_data = simpleStringUtils.getShort(orderby, ordertype, page, size);

            Specification<Order> spec = (root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();

                if (completed != null) {
                    predicates.add(criteriaBuilder.equal(root.get("completed"), completed));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            };

            Page<Order> list = orderRepository.findAll(spec, show_data);

            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
