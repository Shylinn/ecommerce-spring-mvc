package com.nhom3.ecommerceadmin.security;

import com.nhom3.ecommerceadmin.models.Staff;
import com.nhom3.ecommerceadmin.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component

public class SecurityUtil {
    private static StaffService staffService;
    @Autowired
    public SecurityUtil(StaffService staffService) {
        SecurityUtil.staffService = staffService;
    }
    public static String getSessionUser() {
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        }
        return null;
    }
}
