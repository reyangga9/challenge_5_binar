package com.example.Challenge_4.mvc.repository;


import com.example.Challenge_4.mvc.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
//public interface EmployeeRepository extends PagingAndSortingRepository<Employee,Long> {
//public interface ProductRepository extends JpaRepository<Product, UUID> {
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

    @Query(value = "select c from Product c WHERE c.id = :id", nativeQuery = false)
    public Product getById(@Param("id") Long id);

    List<Product> findByMerchant_Id(UUID merchantId);
}
