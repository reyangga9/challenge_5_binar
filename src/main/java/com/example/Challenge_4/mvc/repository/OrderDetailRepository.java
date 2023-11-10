package com.example.Challenge_4.mvc.repository;

import com.example.Challenge_4.mvc.entity.Order;
import com.example.Challenge_4.mvc.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
//public interface EmployeeRepository extends PagingAndSortingRepository<Employee,Long> {
public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID>, JpaSpecificationExecutor<OrderDetail> {

    @Query(value = "select c from OrderDetail c WHERE c.id = :id", nativeQuery = false)
    public OrderDetail getById(@Param("id") Long id);

    List<OrderDetail> findByOrder_User_Id(UUID userId);

}
