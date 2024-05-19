package com.nhom3.ecommerceadmin.repository;

import com.nhom3.ecommerceadmin.models.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Staff findByEmail(String email);
    Staff findByUsername(String userName);
    Staff findFirstByUsername(String username);
}
