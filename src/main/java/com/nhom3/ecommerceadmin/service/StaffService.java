package com.nhom3.ecommerceadmin.service;

import com.nhom3.ecommerceadmin.dto.RegistrationDto;
import com.nhom3.ecommerceadmin.models.Staff;

public interface StaffService {
    void saveStaff(RegistrationDto registrationDto);
    Staff findByEmail(String email);
    Staff findByUsername(String username);
}
