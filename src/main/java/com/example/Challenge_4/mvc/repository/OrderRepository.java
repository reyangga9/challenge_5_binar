package com.example.Challenge_4.mvc.repository;

import com.example.Challenge_4.mvc.entity.Merchant;
import com.example.Challenge_4.mvc.entity.Order;
import com.example.Challenge_4.mvc.entity.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.UUID;

@Repository
//public interface EmployeeRepository extends PagingAndSortingRepository<Employee,Long> {

public interface OrderRepository extends JpaRepository<Order, UUID>, JpaSpecificationExecutor<Order> {

    @Query(value = "select c from Order c WHERE c.id = :id", nativeQuery = false)
    public Order getById(@Param("id") Long id);


    List<Order> findByUserId(UUID userId);


    // Modified method with Pageable
    Page<Order> findByUserId(UUID userId, Pageable pageable);

}
