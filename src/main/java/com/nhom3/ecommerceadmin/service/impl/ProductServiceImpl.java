package com.nhom3.ecommerceadmin.service.impl;

import com.nhom3.ecommerceadmin.dto.ProductDto;
import com.nhom3.ecommerceadmin.models.Product;
import com.nhom3.ecommerceadmin.repository.ProductRepository;
import com.nhom3.ecommerceadmin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.nhom3.ecommerceadmin.mapper.ProductMapper.mapToProduct;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product saveProduct(ProductDto productDto) {
        Product product = mapToProduct(productDto);
        return productRepository.save(product);
    }
}
