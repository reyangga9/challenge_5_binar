package com.example.Challenge_4.mvc.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Challenge_4.mvc.entity.User;

//Long merefer kepada Id (primary key) pada Karyawan Repository yang d di Entity KaryawanDB
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    // Contoh Dari JPA Query
    @Query(value = "select c from User c WHERE c.id = :idUser")
    public User getById(@Param("idUser") UUID idUser);

//    // NativeQeury : menggunakan JPQL ATAU JPQ QUERY
//    // Perbedaaannya adalah kalo Nativequery select Table Bukan CLASSS Entity
//    // dan beda field
//    @Query(value = "select e from user e WHERE id = :idUser", nativeQuery = true)
//    public Object getByIdNativeQuery(@Param("idUser") UUID idUser);
}
