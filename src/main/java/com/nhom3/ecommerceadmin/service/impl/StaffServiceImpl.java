package com.nhom3.ecommerceadmin.service.impl;

import com.nhom3.ecommerceadmin.dto.RegistrationDto;
import com.nhom3.ecommerceadmin.models.Role;
import com.nhom3.ecommerceadmin.models.Staff;
import com.nhom3.ecommerceadmin.repository.StaffRepository;
import com.nhom3.ecommerceadmin.service.StaffService;
import com.nhom3.ecommerceadmin.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

@Service
public class StaffServiceImpl implements StaffService {
    private StaffRepository staffRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public StaffServiceImpl(StaffRepository staffRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.staffRepository = staffRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveStaff(RegistrationDto registrationDto) {
        Staff staff = new Staff();
        staff.setFullName(registrationDto.getFullName());
        staff.setUsername(registrationDto.getUsername());
        staff.setEmail(registrationDto.getEmail());
        staff.setJoinOn(registrationDto.getJoinOn());
        staff.setPhoneNum(registrationDto.getPhoneNum());
        staff.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_STAFF");
        staff.setRoles(Collections.singletonList(role));
        staffRepository.save(staff);
    }

    @Override
    public Staff findByEmail(String email) {
        return staffRepository.findByEmail(email);
    }

    @Override
    public Staff findByUsername(String username) {
        return staffRepository.findByUsername(username);
    }
}
