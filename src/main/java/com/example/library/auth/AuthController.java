package com.example.library.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Validated
public class AuthController {

	private final UserService userService;

	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/register")
	public String registerForm(Model model) {
		model.addAttribute("form", new RegisterForm());
		return "register";
	}

	@PostMapping("/register")
	public String registerSubmit(@ModelAttribute("form") RegisterForm form, Model model) {
		if (form.getUsername() == null || form.getUsername().isBlank() || form.getPassword() == null || form.getPassword().length() < 6) {
			model.addAttribute("error", "Username and password (min 6 chars) are required");
			return "register";
		}
		userService.register(form.getUsername(), form.getPassword(), form.getFullName(), form.getEmail(), form.getUserType());
		model.addAttribute("registered", true);
		return "login";
	}

	@GetMapping("/logout")
	public String logout() {
		// Spring Security will handle the logout automatically
		return "redirect:/?logout=true";
	}

	public static class RegisterForm {
		@NotBlank
		private String username;
		@NotBlank
		@Size(min = 6, message = "Password must be at least 6 characters")
		private String password;
		private String fullName;
		@Email
		private String email;
		private String userType;

		public String getUsername() { return username; }
		public void setUsername(String username) { this.username = username; }
		public String getPassword() { return password; }
		public void setPassword(String password) { this.password = password; }
		public String getFullName() { return fullName; }
		public void setFullName(String fullName) { this.fullName = fullName; }
		public String getEmail() { return email; }
		public void setEmail(String email) { this.email = email; }
		public String getUserType() { return userType; }
		public void setUserType(String userType) { this.userType = userType; }
	}
}




