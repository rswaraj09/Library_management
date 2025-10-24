package com.example.library.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String getDashboard(Authentication authentication) {
        if (authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))) {
            return "librarian-dashboard";
        }
        return "student-dashboard";
    }
}