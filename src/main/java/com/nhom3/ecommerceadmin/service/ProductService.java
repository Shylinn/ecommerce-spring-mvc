package com.nhom3.ecommerceadmin.service;

import com.nhom3.ecommerceadmin.dto.ProductDto;
import com.nhom3.ecommerceadmin.models.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(ProductDto productDto);

    List<ProductDto> findAllProducts();
}