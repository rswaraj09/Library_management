package com.example.library.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	@SuppressWarnings("deprecation")
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.sessionManagement(session -> session
					.maximumSessions(1)
					.maxSessionsPreventsLogin(false)
			)
			.authorizeHttpRequests(auth -> auth
					.requestMatchers("/", "/login", "/register", "/styles.css", "/script.js", "/advanced-search", "/digital-records", "/due-date-tracking", "/reservation-system", "/secure-access", "/year-categories", "/debug-auth", "/librarian-dashboard", "/student-dashboard").permitAll()
					.requestMatchers("/books", "/books/category/*", "/books/year/*").permitAll()
					.requestMatchers("POST", "/books").hasRole("LIBRARIAN")
					.requestMatchers("DELETE", "/books/*").hasRole("LIBRARIAN")
					.requestMatchers("PUT", "/books/*").hasRole("LIBRARIAN")
					.anyRequest().authenticated()
			)
			.formLogin(form -> form
					.loginPage("/login").permitAll()
					.defaultSuccessUrl("/", true)
					.failureUrl("/login?error=true")
			)
			.logout(logout -> logout
					.logoutUrl("/logout")
					.logoutSuccessUrl("/")
					.invalidateHttpSession(true)
					.deleteCookies("JSESSIONID")
					.permitAll()
			);
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}




