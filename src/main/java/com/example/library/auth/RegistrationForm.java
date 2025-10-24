package com.example.library.auth;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public class RegistrationForm {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    private User.UserRole role = User.UserRole.USER;
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public User.UserRole getRole() { return role; }
    public void setRole(User.UserRole role) { this.role = role; }
}