package com.nhom3.ecommerceadmin.controller;

import com.nhom3.ecommerceadmin.dto.RegistrationDto;
import com.nhom3.ecommerceadmin.models.Staff;
import com.nhom3.ecommerceadmin.service.StaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class AuthController {
    private StaffService staffService;

    public AuthController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        RegistrationDto staff = new RegistrationDto();
        model.addAttribute("staff", staff);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("staff")RegistrationDto staff,
                           BindingResult result, Model model) {
        Staff existingUserEmail = staffService.findByEmail(staff.getEmail());
        if(existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()) {
            return "redirect:/register?fail";
        }
        Staff existingUserUsername = staffService.findByUsername(staff.getUsername());
        if(existingUserUsername != null && existingUserUsername.getUsername() != null && !existingUserUsername.getUsername().isEmpty()) {
            return "redirect:/register?fail";
        }
        if(result.hasErrors()) {
            model.addAttribute("staff", staff);
            return "register";
        }
        staffService.saveStaff(staff);
        return "redirect:/login?success";
    }
}