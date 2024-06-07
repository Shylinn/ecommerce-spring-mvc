package com.nhom3.ecommerceadmin.controller;

import com.nhom3.ecommerceadmin.dto.BillCreateDto;
import com.nhom3.ecommerceadmin.dto.BillDto;
import com.nhom3.ecommerceadmin.dto.ProductDto;
import com.nhom3.ecommerceadmin.models.Bill;
import com.nhom3.ecommerceadmin.models.Customer;
import com.nhom3.ecommerceadmin.models.Image;
import com.nhom3.ecommerceadmin.models.Product;
import com.nhom3.ecommerceadmin.repository.CustomerRepository;
import com.nhom3.ecommerceadmin.repository.ImageRepository;
import com.nhom3.ecommerceadmin.service.BillService;
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
public class BillController {
    private BillService billService;
    private ProductService productService;
    private CustomerRepository customerRepository;

    @Autowired
    public BillController(BillService billService,ProductService productService, CustomerRepository customerRepository) {
        this.billService = billService;
        this.productService = productService;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/bills/{billId}")
    public String billDetail(@PathVariable("billId") Long billId, Model model) {
        BillDto billDto = billService.findBillById(billId);
        model.addAttribute("bill", billDto);
        return "bill-details";
    }

//    @GetMapping("/products/{productId}/edit")
//    public String createProductForm(@PathVariable("productId") Long productId, Model model) {
//        ProductDto product = productService.findProductById(productId);
//        model.addAttribute("product", product);
//        return "product-edit";
//    }
//
    @GetMapping("/bill-details/{billId}/edit")
    public String editBillDetails(@PathVariable("billId") Long billId, Model model) {
        List<ProductDto> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "redirect:/index?TamThoiChuyenDenTrangIndex";
    }

    @GetMapping("/bills/new")
    public String createBIllForm(Model model) {
        Bill bill = new Bill();
        List<Customer> customers = customerRepository.findAll();
        model.addAttribute("bill", bill);
        model.addAttribute("customers", customers);
        return "bill-create";
    }

    @GetMapping("/bills")
    public String listBillPage(Model model) {
        List<BillDto> bills = billService.findAllBills();
        model.addAttribute("bills", bills);
        return "bill-list";
    }
    @PostMapping("/bills/delete")
    public String deleteBill(@RequestParam("id") Long id) {
        billService.deleteBillById(id);
        return "redirect:/bills?deleteSuccess";
    }

    @PostMapping("/bills/new")
    public String saveBill(@Valid @ModelAttribute("bill") BillCreateDto bill
            , BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "bill-create";
        }
        Bill createdBill = billService.saveBill(bill);
        model.addAttribute("bill", bill);
        return "redirect:/bill-details/" + createdBill.getId()+ "/edit?billCreateSuccess";
    }

//    @PostMapping("/products/update")
//    public String updateProduct(@Valid @ModelAttribute("product") ProductDto productDto
//            , BindingResult result, Model model, @RequestParam("photo") MultipartFile photo
//            , @RequestParam("delete-image") String isDeleteImage) {
//        if(result.hasErrors()) {
////            model.addAttribute("product", productDto);
//            return "product-edit";
//        }
//        setPhotoUrl(productDto, photo);
//        if(photo.isEmpty() && Objects.equals(isDeleteImage, "true")){
//            productDto.setPhotoUrl("");
//        }
//
//        productService.saveProduct(productDto);
//        return "redirect:/products/" +productDto.getId() + "?productUpdateSuccess";
//    }
}