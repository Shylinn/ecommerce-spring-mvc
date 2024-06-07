package com.nhom3.ecommerceadmin.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.nhom3.ecommerceadmin.dto.ProductDto;
import com.nhom3.ecommerceadmin.dto.RegistrationDto;
import com.nhom3.ecommerceadmin.models.Staff;
import com.nhom3.ecommerceadmin.service.StaffService;

import jakarta.validation.Valid;

@Controller
public class StaffController {

	@Autowired
	private StaffService staffService;

	@GetMapping("/staff/add")
	public String createUserForm(Model model) {
		Staff staff = new Staff();
		model.addAttribute("staff", staff);
//      model.addAttribute("productActive",true);
		return "user-create";
	}

	@PostMapping("/staff/new")
	public String saveUser(@Valid @ModelAttribute("staff") RegistrationDto userDto, BindingResult result, Model model) {
		if (result.hasErrors()) {
//          model.addAttribute("product", productDto);
			return "user-create";
		}
		Staff existingUserEmail = staffService.findByEmail(userDto.getEmail());
        if(existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()) {
            return "redirect:/staff/add?fail";
        }
        Staff existingUserUsername = staffService.findByUsername(userDto.getUsername());
        if(existingUserUsername != null && existingUserUsername.getUsername() != null && !existingUserUsername.getUsername().isEmpty()) {
            return "redirect:/staff/add?fail";
        }
		staffService.saveStaff(userDto);
		return "redirect:/staffs?userCreateSuccess";
	}

	@PostMapping("/staff/delete")
	public String deleteProduct(@RequestParam("id") Long id) {
		staffService.deleteStaffByCode(id);
		return "redirect:/staffs?deleteSuccess";
	}

	@GetMapping("/staffs")
	public String listProductPage(Model model) {
		List<RegistrationDto> staffs = staffService.findAllStaff();
		model.addAttribute("staffs", staffs);
		return "user-list";
	}
	
	@GetMapping("/staff/{userId}")
    public String productDetail(@PathVariable("userId") Long productId, Model model) {
        RegistrationDto registrationDto = staffService.findStaffById(productId);
        registrationDto.setSales(registrationDto.getSales());
        model.addAttribute("staff", registrationDto);
        return "user-details";
    }

    @GetMapping("/staff/{userId}/edit")
    public String createProductForm(@PathVariable("userId") Long productId, Model model) {
    	RegistrationDto registrationDto = staffService.findStaffById(productId);
        model.addAttribute("staff", registrationDto);
        return "user-edit";
    }
    
    @PostMapping("/staff/update")
    public String updateProduct(@Valid @ModelAttribute("staff") RegistrationDto userDto, BindingResult result, Model model) {
        if(result.hasErrors()) {
//            model.addAttribute("product", productDto);
            return "user-edit";
        }

        staffService.saveStaff(userDto);
        return "redirect:/staff/" +userDto.getId() + "?productUpdateSuccess";
    }
}
