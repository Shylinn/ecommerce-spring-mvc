package com.nhom3.ecommerceadmin.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class UserDto {
    private Long id;
    @NotEmpty(message = "Nhập tên nhan vien")
    private String name;
    @NotEmpty(message = "Nhập mã nhan vien")
    private LocalDateTime datework;
    private String phone;
    @NotEmpty(message = "nhap CCCD")
    private String identification;
    @NotNull(message = "Nhập dia chi")
    private String address;
    @NotNull(message = "Nhập doanh so")
    private Long sales;
}