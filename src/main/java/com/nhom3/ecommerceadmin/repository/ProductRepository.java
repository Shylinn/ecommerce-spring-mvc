package com.nhom3.ecommerceadmin.repository;

import com.nhom3.ecommerceadmin.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
