package com.nhom3.ecommerceadmin.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String photoUrl;
    @NotEmpty
    private String unit;
    @NotNull
    private Long price;
    private String author;
    private String publisher;
    private String genre;
}