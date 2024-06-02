package com.nhom3.ecommerceadmin.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class HandleErrorController implements ErrorController {

    @ExceptionHandler(value = { Exception.class, RuntimeException.class })
    @ResponseStatus
    public String handleException(HttpServletRequest request) {
        return "404";
    }
}
