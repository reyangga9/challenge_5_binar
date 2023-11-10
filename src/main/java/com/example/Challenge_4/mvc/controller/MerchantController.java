package com.example.Challenge_4.mvc.controller;

import com.example.Challenge_4.mvc.entity.Merchant;
import com.example.Challenge_4.mvc.entity.User;
import com.example.Challenge_4.mvc.repository.MerchantRepository;
import com.example.Challenge_4.mvc.service.MerchantService;
import com.example.Challenge_4.mvc.utils.SimpleStringUtils;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/merchants")
public class MerchantController {

    SimpleStringUtils simpleStringUtils = new SimpleStringUtils();

    @Autowired
    private MerchantService merchantService;

    @Autowired
public MerchantRepository merchantRepository;


    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Map> save(@RequestBody Merchant request) {

        return new ResponseEntity<Map>(merchantService.save(request), HttpStatus.OK);
    }


    @PutMapping(value = {"/update", "/update/"})
    public ResponseEntity<Map> update(@RequestBody Merchant request) {
        return new ResponseEntity<Map>(merchantService.update(request), HttpStatus.OK);
    }


    @GetMapping("/getAll/{merchantId}")
    public ResponseEntity<Map<String, Object>> getMerchantAndProducts(@PathVariable UUID merchantId) {
        Map<String, Object> result = merchantService.getMerchantAndProducts(merchantId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteMerchant(@PathVariable UUID id) {
        Map<String, Object> response = merchantService.delete(id);
        HttpStatus httpStatus = (Boolean) response.get("success") ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getMerchantById(@PathVariable UUID id) {
        Map<String, Object> response = merchantService.getById(id);
        HttpStatus httpStatus = (Boolean) response.get("success") ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/all")
    public List<Merchant> getAllMerchant() {
        List<Merchant> merchants = merchantService.getAllMerchants();
        return merchants;
    }

    @GetMapping(value = {"/list-spec", "/list-spec/"})
    public ResponseEntity<Page<Merchant>> listSpec(
            @RequestParam() Integer page,
            @RequestParam(required = true) Integer size,
            @RequestParam(required = false) Boolean open,
            @RequestParam(required = false) String orderby,
            @RequestParam(required = false) String ordertype) {

        try {
            Pageable show_data = simpleStringUtils.getShort(orderby, ordertype, page, size);

            Specification<Merchant> spec = (root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();

                if (open != null) {
                    predicates.add(criteriaBuilder.equal(root.get("open"), open));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            };

            Page<Merchant> list = merchantRepository.findAll(spec, show_data);

            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

