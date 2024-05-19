package com.nhom3.ecommerceadmin.controller;

import com.nhom3.ecommerceadmin.models.Customer;
import com.nhom3.ecommerceadmin.repository.CustomerRepository;
import com.nhom3.ecommerceadmin.service.ExcelExportService;
import com.nhom3.ecommerceadmin.service.ExcelImportService;

import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ExcelExportService excelExportService;
    @Autowired
    private ExcelImportService importExcel;

    @RequestMapping("/customers")
    public String ListCustomerPage(ModelMap modelmap) {
        Iterable<Customer> customers = customerRepository.findAll();
        modelmap.addAttribute("customers", customers);
        return "customer-list";
    }

    @RequestMapping("customer/delete/{id}")
    public String deleteCustomerById(@PathVariable(value = "id") long id, ModelMap modelmap) {
        this.customerRepository.deleteById(id);
        Iterable<Customer> customers = customerRepository.findAll();
        modelmap.addAttribute("customers", customers);
        return "redirect:/customers";

    }

    @GetMapping("/customer/add")
    public String showAddCustomerForm(ModelMap modelmap) {
        modelmap.addAttribute("customer", new Customer());
        return "customer-create";
    }

    @GetMapping("/customers/checkDuplicate")
    public ResponseEntity<Boolean> checkDuplicateCustomer(@RequestParam("fullName") String fullName) {
        // Perform database query to check if the customer exists
        boolean customerExists = customerRepository.existsByFullName(fullName);

        if (customerExists) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    @PostMapping("/customer/add")
    public String addCustomer(@ModelAttribute("customer") Customer customer, BindingResult result) {
        if (customerRepository.existsByFullName(customer.getFullName())) {
            result.rejectValue("fullName", "error.customer", "Tên khách hàng đã tồn tại");
            return "customer-create";
        }

        customerRepository.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/customer/update/{id}")
    public String updateCustomer(@PathVariable(value = "id") long id, ModelMap modelmap) {
        Optional<Customer> customer = this.customerRepository.findById(id);

        modelmap.addAttribute("customer", customer.get());
        return "customer-update";
    }

    @PostMapping("/customer/save/{id}")
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerRepository.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/customers/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        Iterable<Customer> customers = (Iterable<Customer>) customerRepository.findAll();
        excelExportService.exportToExcel(response, customers);
    }

    @PostMapping("/customers/upload")
    public String importExcel(@RequestParam("file") MultipartFile file) {
        List<Customer> customerList;
        try {
            customerList = importExcel.excelToCustomerList(file);
            System.out.println(customerList.toString());
            customerRepository.saveAll(customerList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/customers";
    }

}
