package com.nhom3.ecommerceadmin.mapper;

import com.nhom3.ecommerceadmin.dto.RegistrationDto;
import com.nhom3.ecommerceadmin.models.Staff;

public class StaffMapper {
	
    public static RegistrationDto mapToStaffDto(Staff staff){
    	RegistrationDto registrationDto = new  RegistrationDto();
    	registrationDto.setId(staff.getId());
    	registrationDto.setFullName(staff.getFullName());
    	registrationDto.setUsername(staff.getUsername());
    	registrationDto.setEmail(staff.getEmail());
    	registrationDto.setPassword(staff.getPassword());
    	registrationDto.setJoinOn(staff.getJoinOn());
    	registrationDto.setPhoneNum(staff.getPhoneNum());
    	registrationDto.setIdCardNum(staff.getIdCardNum());
    	registrationDto.setAddress(staff.getAddress());
    	registrationDto.setSales(staff.getSales());
        return registrationDto;
    }
}
