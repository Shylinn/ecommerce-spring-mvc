package com.nhom3.ecommerceadmin.controller;

import com.nhom3.ecommerceadmin.dto.ProductDto;
import com.nhom3.ecommerceadmin.models.Image;
import com.nhom3.ecommerceadmin.models.Product;
import com.nhom3.ecommerceadmin.repository.ImageRepository;
import com.nhom3.ecommerceadmin.service.ProductService;
import com.nhom3.ecommerceadmin.service.StaffService;
import jakarta.servlet.http.HttpServletResponse;
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
import java.util.Objects;

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

    @GetMapping("/products/{productId}")
    public String productDetail(@PathVariable("productId") Long productId, Model model) {
        ProductDto productDto = productService.findProductById(productId);
        model.addAttribute("product", productDto);
        return "product-details";
    }

    @GetMapping("/products/{productId}/edit")
    public String createProductForm(@PathVariable("productId") Long productId, Model model) {
        ProductDto product = productService.findProductById(productId);
        model.addAttribute("product", product);
        return "product-edit";
    }

    @GetMapping("/products/new")
    public String createProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
//        model.addAttribute("productActive",true);
        return "product-create";
    }

    @GetMapping("/products")
    public String listProductPage(Model model) {
        List<ProductDto> products = productService.findAllProducts();
        model.addAttribute("products", products);
//        model.addAttribute("productActive",true);
        return "product-list";
    }

    @GetMapping("/products/upload")
    public String showUploadForm() {
        return "product-upload"; // Trả về trang HTML có form upload
    }

    @PostMapping("/products/delete")
    public String deleteProduct(@RequestParam("id") Long id) {
        productService.deleteProductById(id);
        return "redirect:/products?deleteSuccess";
    }
    @PostMapping("/products/upload")
    public String uploadProducts(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            // Xử lý trường hợp người dùng không chọn file
            return "redirect:/products/upload?fileEmpty";
        }

        try {
            // Gọi phương thức của ProductService để xử lý việc thêm sản phẩm từ file Excel
            productService.addProductsFromExcel(file);
            return "redirect:/products?uploadSuccess";
        } catch (Exception e) {
            // Xử lý trường hợp có lỗi xảy ra trong quá trình thêm sản phẩm từ file Excel
            return "redirect:/products/upload?error=" + e.getMessage();
        }
    }

    @GetMapping("/products/download")
    public void downloadProducts(HttpServletResponse response) throws IOException {
        productService.exportProductsToExcel(response);
    }

    @PostMapping("/products/new")
    public String saveProduct(@Valid @ModelAttribute("product") ProductDto productDto
            , BindingResult result, Model model, @RequestParam("photo") MultipartFile photo) {
        if(result.hasErrors()) {
//            model.addAttribute("product", productDto);
            return "product-create";
        }
        setPhotoUrl(productDto, photo);
        productService.saveProduct(productDto);
        return "redirect:/products?productCreateSuccess";
    }

    @PostMapping("/products/update")
    public String updateProduct(@Valid @ModelAttribute("product") ProductDto productDto
            , BindingResult result, Model model, @RequestParam("photo") MultipartFile photo
            , @RequestParam("delete-image") String isDeleteImage) {
        if(result.hasErrors()) {
//            model.addAttribute("product", productDto);
            return "product-edit";
        }
        setPhotoUrl(productDto, photo);
        if(photo.isEmpty() && Objects.equals(isDeleteImage, "true")){
            productDto.setPhotoUrl("");
        }

        productService.saveProduct(productDto);
        return "redirect:/products/" +productDto.getId() + "/?productUpdateSuccess";
    }

    private void setPhotoUrl(@ModelAttribute("product") @Valid ProductDto productDto, @RequestParam("photo") MultipartFile photo) {
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
    }
}