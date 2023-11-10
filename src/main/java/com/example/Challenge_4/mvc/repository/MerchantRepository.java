package com.example.Challenge_4.mvc.repository;

import com.example.Challenge_4.mvc.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
//public interface EmployeeRepository extends PagingAndSortingRepository<Employee,Long> {

public interface MerchantRepository extends JpaRepository<Merchant, UUID>, JpaSpecificationExecutor<Merchant> {

    @Query(value = "select c from Merchant c WHERE c.id = :id", nativeQuery = false)
    public Merchant getById(@Param("id") Long id);


}
