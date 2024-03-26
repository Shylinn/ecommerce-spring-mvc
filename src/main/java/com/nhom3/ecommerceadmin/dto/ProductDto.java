package com.nhom3.ecommerceadmin.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class ProductDto {
    private Long id;
    @NotEmpty(message = "Nhập tên sản phẩm")
    private String name;
    @NotEmpty(message = "Nhập mã sản phẩm")
    private String code;
    private String photoUrl;
    @NotEmpty(message = "Chọn đơn vị tính")
    private String unit;
    @NotNull(message = "Nhập số lượng")
    private String quantity;
    @NotNull(message = "Nhập giá sản phẩm")
    private Long price;
    private String author;
    private String publisher;
    private String genre;
    private String description;
}