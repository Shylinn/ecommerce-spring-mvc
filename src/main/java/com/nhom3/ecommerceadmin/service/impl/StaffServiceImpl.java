package com.nhom3.ecommerceadmin.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nhom3.ecommerceadmin.dto.RegistrationDto;
import com.nhom3.ecommerceadmin.mapper.StaffMapper;
import com.nhom3.ecommerceadmin.models.Role;
import com.nhom3.ecommerceadmin.models.Staff;
import com.nhom3.ecommerceadmin.repository.RoleRepository;
import com.nhom3.ecommerceadmin.repository.StaffRepository;
import com.nhom3.ecommerceadmin.service.StaffService;

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
        if(registrationDto.getId() != null) {
        	staff.setId(registrationDto.getId());
        }
        staff.setFullName(registrationDto.getFullName());
        staff.setUsername(registrationDto.getUsername());
        staff.setEmail(registrationDto.getEmail());
        staff.setJoinOn(registrationDto.getJoinOn());
        staff.setPhoneNum(registrationDto.getPhoneNum());
        staff.setIdCardNum(registrationDto.getIdCardNum());
        staff.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        staff.setAddress(registrationDto.getAddress());
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
    
	@Override
	public List<RegistrationDto> findAllStaff() {
		List<Staff> products = staffRepository.findAll();
	    return products.stream().map(StaffMapper::mapToStaffDto).collect(Collectors.toList());
	}

	@Transactional
	@Override
	public void deleteStaffByCode(Long id) {
		staffRepository.deleteById(id);
	}

	@Override
	public RegistrationDto findStaffById(Long id) {
		return StaffMapper.mapToStaffDto(staffRepository.findById(id).orElse(new Staff()));
	}
}
