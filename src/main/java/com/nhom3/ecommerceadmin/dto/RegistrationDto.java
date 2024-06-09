package com.nhom3.ecommerceadmin.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegistrationDto {
    private Long id;
    @NotBlank(message = "Nhập họ và tên")
    private String fullName;
    @NotBlank(message = "Nhập tên đăng nhập")
    private String username;
    @NotBlank(message = "Nhập email")
    private String email;
    @NotEmpty(message = "Nhập mật khẩu")
    private String password;
    @NotEmpty(message = "Nhập lại mật khẩu")
    private String passwordConfirm;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinOn;  // ngày vào làm ở công ty
    private String phoneNum;
    private String idCardNum;
    private String address;
    private Double sales;
}