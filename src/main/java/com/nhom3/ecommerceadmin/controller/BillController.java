package com.nhom3.ecommerceadmin.controller;

import com.nhom3.ecommerceadmin.models.Bill;
import com.nhom3.ecommerceadmin.models.BillDetails;
import com.nhom3.ecommerceadmin.models.Customer;
import com.nhom3.ecommerceadmin.models.Staff;
import com.nhom3.ecommerceadmin.repository.BillDetailRepository;
import com.nhom3.ecommerceadmin.repository.BillRepository;
import com.nhom3.ecommerceadmin.repository.CustomerRepository;
import com.nhom3.ecommerceadmin.repository.ProductRepository;
import com.nhom3.ecommerceadmin.repository.StaffRepository;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BillController {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private BillDetailRepository billDetailRepository;

    @Autowired
    private BillRepository billRepository;
    @Autowired
    private ProductRepository productRepository;

    @RequestMapping("/bills")
    public String listBillPage(ModelMap modelmap) {
        List<Bill> bills = billRepository.findAll();

        List<Bill> billsWithDetails = bills.stream().map(bill -> {
            // Retrieve customer and set customer name
            Customer customer = customerRepository.findById(bill.getCustomer().getId()).orElse(null);
            if (customer != null) {
                bill.setCustomerName(customer.getFullName());
            } else {
                bill.setCustomerName("Unknown Customer");
            }

            // Retrieve employee and set employee name
            Staff staff = staffRepository.findById(bill.getStaff().getId()).orElse(null);
            if (staff != null) {
                bill.setStaffName(staff.getFullName());
            } else {
                bill.setStaffName("Unknown Employee");
            }

            // Retrieve bill details and calculate total price for each detail
            List<BillDetails> billDetails = billDetailRepository.findAllByBillId(bill.getId());
            billDetails.forEach(detail -> {
                detail.setProduct(productRepository.findById(detail.getProduct().getId())
                        .orElseThrow(() -> new RuntimeException(
                                "Product not found with id: " + detail.getProduct().getId())));
                detail.calculateTotalPrice(); // Calculate total price based on quantity and product price
            });

            bill.setDetails(new HashSet<>(billDetails));
            bill.setValue(bill.getTotalAmount()); // Calculate and set the total amount
            return bill;
        }).collect(Collectors.toList());

        modelmap.addAttribute("bills", billsWithDetails);

        return "bill-list";
    }

    @RequestMapping("bill/delete/{id}")
    public String deleteBillById(@PathVariable(value = "id") long id, ModelMap modelmap) {
        this.billRepository.deleteById(id);

        List<Bill> bills = billRepository.findAll();
        if (bills.isEmpty()) {

            modelmap.addAttribute("bills", bills);

            return "redirect:/bills";

        } else {
            List<Bill> billsWithDetails = bills.stream().map(bill -> {
                // Retrieve customer and set customer name
                Customer customer = customerRepository.findById(bill.getCustomer().getId()).orElse(null);
                if (customer != null) {
                    bill.setCustomerName(customer.getFullName());
                } else {
                    bill.setCustomerName("Unknown Customer");
                }

                // Retrieve employee and set employee name
                Staff staff = staffRepository.findById(bill.getStaff().getId()).orElse(null);
                if (staff != null) {
                    bill.setStaffName(staff.getFullName());
                } else {
                    bill.setStaffName("Unknown Employee");
                }

                // Retrieve bill details and calculate total price for each detail
                List<BillDetails> billDetails = billDetailRepository.findAllByBillId(bill.getId());
                billDetails.forEach(detail -> {
                    detail.setProduct(productRepository.findById(detail.getProduct().getId())
                            .orElseThrow(() -> new RuntimeException(
                                    "Product not found with id: " + detail.getProduct().getId())));
                    detail.calculateTotalPrice(); // Calculate total price based on quantity and product price
                });

                bill.setDetails(new HashSet<>(billDetails));
                bill.setValue(bill.getTotalAmount()); // Calculate and set the total amount
                return bill;
            }).collect(Collectors.toList());

            modelmap.addAttribute("bills", billsWithDetails);

            return "redirect:/bills";
        }
    }

    // @GetMapping("/bill/add")
    // public String showAddBillForm(ModelMap modelmap) {
    // modelmap.addAttribute("customers", customerRepository.findAll());
    // modelmap.addAttribute("staffs", staffRepository.findAll());
    // modelmap.addAttribute("products", productRepository.findAll());
    // modelmap.addAttribute("bill", new Bill());
    // return "bill-create";
    // }

    // @PostMapping("/bill/add")
    // public String addCustomer(@ModelAttribute("customer") Customer customer,
    // BindingResult result) {
    // if (customerRepository.existsByFullName(customer.getFullName())) {
    // result.rejectValue("fullName", "error.customer", "Tên khách hàng đã tồntại");
    // return "customer-create";
    // }

    // customerRepository.save(customer);
    // return "redirect:/customers";
    // }

}