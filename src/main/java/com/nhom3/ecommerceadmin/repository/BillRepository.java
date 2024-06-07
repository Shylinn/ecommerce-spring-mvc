package com.nhom3.ecommerceadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhom3.ecommerceadmin.models.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {
    
}
