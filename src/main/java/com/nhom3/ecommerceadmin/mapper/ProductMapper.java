package com.nhom3.ecommerceadmin.mapper;

import com.nhom3.ecommerceadmin.dto.ClubDto;
import com.nhom3.ecommerceadmin.dto.ProductDto;
import com.nhom3.ecommerceadmin.models.Club;
import com.nhom3.ecommerceadmin.models.Product;

import java.util.stream.Collectors;

import static com.nhom3.ecommerceadmin.mapper.EventMapper.mapToEventDto;

public class ProductMapper {

    public static Product mapToProduct(ProductDto productDto){
        Product product = Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .photoUrl(productDto.getPhotoUrl())
                .unit(productDto.getUnit())
                .price(productDto.getPrice())
                .author(productDto.getAuthor())
                .publisher(productDto.getPublisher())
                .genre(productDto.getGenre())
                .build();
        return product;
    }

    public static ProductDto mapToProductDto(Product product){
        ProductDto productDto = ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .photoUrl(product.getPhotoUrl())
                .unit(product.getUnit())
                .price(product.getPrice())
                .author(product.getAuthor())
                .publisher(product.getPublisher())
                .genre(product.getGenre())
                .build();
        return productDto;
    }

}
