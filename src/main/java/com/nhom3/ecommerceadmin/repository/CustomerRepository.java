package com.nhom3.ecommerceadmin.repository;

import com.nhom3.ecommerceadmin.models.Customer;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    boolean existsByFullName(String fullName);

    List<Customer> findByFullNameContaining(String keyword);

  
}
