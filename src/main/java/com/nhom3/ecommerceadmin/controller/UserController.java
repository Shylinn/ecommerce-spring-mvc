package com.nhom3.ecommerceadmin.controller;

import com.nhom3.ecommerceadmin.dto.UserDto;
import com.nhom3.ecommerceadmin.models.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserController {
    @GetMapping("/user/add")
    public String createUserForm(Model model) {
        User user = new User ();
        model.addAttribute("user",user);
//        model.addAttribute("productActive",true);
        return "user-create";
            }
    @PostMapping("/user/new")
    public String saveUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
        if(result.hasErrors()) {
//            model.addAttribute("product", productDto);
            return "user-create";
        }
        // procutService.saveProduct(UserDto);
        return "redirect:/products?productCreateSuccess";
    }
}
