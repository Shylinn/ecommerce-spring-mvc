package com.nhom3.ecommerceadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController {

    @GetMapping("/access-deny")
    public String handleError403() {

        return "error/403";

    }

}