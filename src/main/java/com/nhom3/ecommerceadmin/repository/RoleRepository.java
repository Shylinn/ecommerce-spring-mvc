package com.nhom3.ecommerceadmin.repository;

import com.nhom3.ecommerceadmin.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
