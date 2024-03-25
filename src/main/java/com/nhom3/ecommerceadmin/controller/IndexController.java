package com.nhom3.ecommerceadmin.controller;

import com.nhom3.ecommerceadmin.dto.ClubDto;
import com.nhom3.ecommerceadmin.models.Staff;
import com.nhom3.ecommerceadmin.security.SecurityUtil;
import com.nhom3.ecommerceadmin.service.ClubService;
import com.nhom3.ecommerceadmin.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class IndexController {
    private StaffService staffService;

    @Autowired
    public IndexController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping("/index")
    public String indexPage(Model model){
        Staff staff = new Staff();
        String username = SecurityUtil.getSessionUser();
        if(username != null) {
            staff = staffService.findByUsername(username);
            model.addAttribute("staff", staff);
        }
        model.addAttribute("staff", staff);
        return "index";
    }

}
