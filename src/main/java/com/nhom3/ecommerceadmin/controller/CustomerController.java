package com.nhom3.ecommerceadmin.controller;

import com.nhom3.ecommerceadmin.models.Customer;
import com.nhom3.ecommerceadmin.repository.CustomerRepository;

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

@Controller
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

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

    @GetMapping("/customer/search")
    public String searchCustomer(@RequestParam("fullName") String fullName, ModelMap modelmap) {
        Iterable<Customer> customers = customerRepository.findByFullNameContaining(fullName);
        modelmap.addAttribute("customers", customers);
        return "customer-list";
    }
}
