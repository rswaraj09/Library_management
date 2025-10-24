package com.example.library.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {
    
    @GetMapping("/api/user/role")
    public Map<String, Boolean> getUserRole(Authentication authentication) {
        boolean isLibrarian = authentication != null && 
                             authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"));
        return Map.of("isLibrarian", isLibrarian);
    }
}