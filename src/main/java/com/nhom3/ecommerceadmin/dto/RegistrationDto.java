package com.nhom3.ecommerceadmin.dto;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class RegistrationDto {
    private Long id;
    @NotEmpty
    private String fullName;
    @NotEmpty
    private String username;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDate joinOn;  // ngày vào làm ở công ty
    private String phoneNum;
    private String idCardNum;
    private String address;
}