package com.nhom3.ecommerceadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class IndexController {
    @GetMapping("/index")
    public String indexPage(){
        return "index";
    }

    @GetMapping("/test")
    public String testPage(Principal principal){
        return principal != null ? "home/homeSignedIn" : "home/homeNotSignedIn";
    }
}
