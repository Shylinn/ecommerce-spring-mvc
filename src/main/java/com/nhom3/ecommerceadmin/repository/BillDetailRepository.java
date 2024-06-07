package com.nhom3.ecommerceadmin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhom3.ecommerceadmin.models.BillDetails;

public interface BillDetailRepository extends JpaRepository<BillDetails, Long> {
    List<BillDetails> findAllByBillId(Long billId);
}
