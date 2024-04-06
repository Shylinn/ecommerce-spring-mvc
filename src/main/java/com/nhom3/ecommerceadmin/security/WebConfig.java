package com.nhom3.ecommerceadmin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.NumberFormat;
import java.util.Locale;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public NumberFormat numberFormat() {
        return NumberFormat.getNumberInstance(new Locale("vi", "VN"));
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new NumberStyleFormatter("#.###"));
    }
}