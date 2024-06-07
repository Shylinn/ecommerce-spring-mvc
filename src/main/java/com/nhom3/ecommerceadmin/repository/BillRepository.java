package com.nhom3.ecommerceadmin.repository;

import com.nhom3.ecommerceadmin.models.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {
}
