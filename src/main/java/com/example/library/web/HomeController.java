package com.example.library.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home(Model model) {
		// Get authenticated user information
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated() &&
			!"anonymousUser".equals(authentication.getPrincipal())) {
			
			model.addAttribute("isAuthenticated", true);
			model.addAttribute("username", authentication.getName());

			// Check if user has librarian role
			boolean isLibrarian = authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals("ROLE_LIBRARIAN"));
			
			// Redirect based on role
			if (isLibrarian) {
				return "librarian-dashboard";
			} else {
				return "student-dashboard";
			}
		} else {
			// Not authenticated, show public home page
			model.addAttribute("isAuthenticated", false);
			model.addAttribute("isLibrarian", false);
			return "home";
		}
	}

	@GetMapping("/advanced-search")
	public String advancedSearch() {
		return "AdvancedSearch";
	}

	@GetMapping("/librarian-dashboard")
	public String librarianDashboard() {
		return "librarian-dashboard";
	}

	@GetMapping("/student-dashboard")
	public String studentDashboard() {
		return "student-dashboard";
	}

	@GetMapping("/debug-auth")
	public String debugAuth(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("auth", authentication);
		model.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal()));
		model.addAttribute("principal", authentication != null ? authentication.getPrincipal() : "null");
		model.addAttribute("authorities", authentication != null ? authentication.getAuthorities() : "null");
		return "debug-auth";
	}

	@GetMapping("/digital-records")
	public String digitalRecords() {
		return "DigitalRecords";
	}

	@GetMapping("/due-date-tracking")
	public String dueDateTracking() {
		return "DueDateTracking";
	}

	@GetMapping("/reservation-system")
	public String reservationSystem() {
		return "ReservationSystem";
	}

	@GetMapping("/secure-access")
	public String secureAccess() {
		return "SecureAccess";
	}

	@GetMapping("/year-categories")
	public String yearCategories() {
		return "YearCategories";
	}
}


