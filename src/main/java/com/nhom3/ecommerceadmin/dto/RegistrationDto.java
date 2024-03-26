package com.nhom3.ecommerceadmin.dto;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class RegistrationDto {
    private Long id;
    @NotEmpty(message = "Nhập họ và tên")
    private String fullName;
    @NotEmpty(message = "Nhập tên đăng nhập")
    private String username;
    @NotEmpty(message = "Nhập email")
    private String email;
    @NotEmpty(message = "Nhập mật khẩu")
    private String password;
    @NotEmpty(message = "Nhập lại mật khẩu")
    private String passwordConfirm;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDate joinOn;  // ngày vào làm ở công ty
    private String phoneNum;
    private String idCardNum;
    private String address;
}