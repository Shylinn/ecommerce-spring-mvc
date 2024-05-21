package com.nhom3.ecommerceadmin.mapper;

import com.nhom3.ecommerceadmin.dto.ProductDto;
import com.nhom3.ecommerceadmin.models.Product;

public class ProductMapper {

    public static Product mapToProduct(ProductDto productDto){
        Product product = Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .code(productDto.getCode())
                .photoUrl(productDto.getPhotoUrl())
                .unit(productDto.getUnit())
                .quantity(productDto.getQuantity())
                .price(productDto.getPrice())
                .author(productDto.getAuthor())
                .publisher(productDto.getPublisher())
                .genre(productDto.getGenre())
                .description(productDto.getDescription())
                .build();
        return product;
    }

    public static ProductDto mapToProductDto(Product product){
        ProductDto productDto = ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .code(product.getCode())
                .photoUrl(product.getPhotoUrl())
                .unit(product.getUnit())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .author(product.getAuthor())
                .publisher(product.getPublisher())
                .genre(product.getGenre())
                .description(product.getDescription())
                .build();
        return productDto;
    }

}
