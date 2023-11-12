package com.example.Challenge_4.mvc.controller;

import com.example.Challenge_4.mvc.entity.Merchant;
import com.example.Challenge_4.mvc.entity.Product;
import com.example.Challenge_4.mvc.repository.ProductRepository;
import com.example.Challenge_4.mvc.service.ProductService;
import com.example.Challenge_4.mvc.utils.SimpleStringUtils;
import jakarta.persistence.criteria.Join;
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
@RequestMapping("/product")
public class ProductController {

    SimpleStringUtils simpleStringUtils = new SimpleStringUtils();

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Map> save(@RequestBody Product request) {
        return new ResponseEntity<Map>(productService.save(request), HttpStatus.OK);
    }

    @PutMapping(value = {"/update", "/update/"})
    public ResponseEntity<Map> update(@RequestBody Product request) {
        return new ResponseEntity<Map>(productService.update(request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable UUID id) {
        Map<String, Object> response = productService.delete(id);
        HttpStatus httpStatus = (Boolean) response.get("success") ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable UUID id) {
        Map<String, Object> response = productService.getByID(id);
        HttpStatus httpStatus = (Boolean) response.get("success") ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return products;
    }

    @GetMapping(value = {"/list-spec", "/list-spec/"})
    public ResponseEntity<Page<Product>> listSpec(
            @RequestParam() Integer page,
            @RequestParam(required = true) Integer size,
            @RequestParam(required = false) Boolean open,
            @RequestParam(required = false) String orderby,
            @RequestParam(required = false) String ordertype) {

        try {
            Pageable show_data = simpleStringUtils.getShort(orderby, ordertype, page, size);

            Page<Product> list = productRepository.findAll((root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();

                if (open != null) {
                    // Join to the merchant
                    Join<Product, Merchant> merchantJoin = root.join("merchant");

                    // Add the condition on the open property of the merchant
                    predicates.add(criteriaBuilder.equal(merchantJoin.get("open"), open));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }, show_data);

            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

