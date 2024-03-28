package com.nhom3.ecommerceadmin.service;

import com.nhom3.ecommerceadmin.dto.ProductDto;
import com.nhom3.ecommerceadmin.models.Product;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    void saveProduct(ProductDto productDto);

    List<ProductDto> findAllProducts();

    void exportProductsToExcel(HttpServletResponse response) throws IOException;
}