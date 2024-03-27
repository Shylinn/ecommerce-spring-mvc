package com.nhom3.ecommerceadmin.controller;

import com.nhom3.ecommerceadmin.dto.ProductDto;
import com.nhom3.ecommerceadmin.models.Image;
import com.nhom3.ecommerceadmin.models.Product;
import com.nhom3.ecommerceadmin.repository.ImageRepository;
import com.nhom3.ecommerceadmin.service.ProductService;
import com.nhom3.ecommerceadmin.service.StaffService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class ProductController {
    private ProductService productService;
    private StaffService staffService;
    private ImageRepository imageRepository;

    @Autowired
    public ProductController(ProductService productService, StaffService staffService, ImageRepository imageRepository) {
        this.productService = productService;
        this.staffService = staffService;
        this.imageRepository = imageRepository;
    }

    @GetMapping("/products/new")
    public String createProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("productActive",true);
        return "product-create";
    }

    @GetMapping("/products")
    public String listProductPage(Model model) {
        List<ProductDto> products = productService.findAllProducts();
        model.addAttribute("products", products);
//        model.addAttribute("productActive",true);
        return "product-list";
    }

    @PostMapping("/products/new")
    public String saveProduct(@Valid @ModelAttribute("product") ProductDto productDto
            , BindingResult result, Model model, @RequestParam("photo") MultipartFile photo) {
        if(result.hasErrors()) {
//            model.addAttribute("product", productDto);
            return "product-create";
        }
        if(!photo.isEmpty()){
            try {
                // Save the uploaded photo to a directory
                Image image = new Image();
                image.setImageName(photo.getOriginalFilename());
                imageRepository.save(image);
                String uploadFolderPath = new ClassPathResource("static/uploadImg").getFile().getAbsolutePath();

                Path path = Paths.get(uploadFolderPath, photo.getOriginalFilename());
                Files.copy(photo.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                productDto.setPhotoUrl(photo.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productService.saveProduct(productDto);
        return "redirect:/index?productCreateSuccess";
    }
}