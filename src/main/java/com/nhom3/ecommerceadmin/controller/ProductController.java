package com.nhom3.ecommerceadmin.controller;

import com.nhom3.ecommerceadmin.dto.ClubDto;
import com.nhom3.ecommerceadmin.dto.ProductDto;
import com.nhom3.ecommerceadmin.models.Club;
import com.nhom3.ecommerceadmin.models.Product;
import com.nhom3.ecommerceadmin.models.Staff;
import com.nhom3.ecommerceadmin.security.SecurityUtil;
import com.nhom3.ecommerceadmin.service.ClubService;
import com.nhom3.ecommerceadmin.service.ProductService;
import com.nhom3.ecommerceadmin.service.StaffService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService, StaffService StaffService) {
        this.productService = productService;
    }


    @GetMapping("/product/new")
    public String createProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "product-create";
    }

    @PostMapping("/product/new")
    public String saveProduct(@Valid @ModelAttribute("product") ProductDto productDto, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("product", productDto);
            return "product-create";
        }
        productService.saveProduct(productDto);
        return "redirect:/index";
    }
}
