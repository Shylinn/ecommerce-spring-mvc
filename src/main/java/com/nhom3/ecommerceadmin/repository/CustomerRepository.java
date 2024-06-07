package com.nhom3.ecommerceadmin.repository;

import com.nhom3.ecommerceadmin.models.Customer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByFullName(String fullName);

    List<Customer> findByFullNameContaining(String keyword);

}
