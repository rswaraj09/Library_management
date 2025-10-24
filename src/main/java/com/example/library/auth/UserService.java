package com.example.library.auth;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

	private final UserRepository users;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository users, PasswordEncoder passwordEncoder) {
		this.users = users;
		this.passwordEncoder = passwordEncoder;
	}

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = users.findByUsername(usernameOrEmail)
                .orElseGet(() -> users.findByEmail(usernameOrEmail)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + usernameOrEmail)));
        List<GrantedAuthority> authorities = users.findAuthorities(user.getUsername())
			.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
				.password(user.getPassword())
				.authorities(authorities)
				.disabled(!user.isEnabled())
				.build();
	}

	public Long register(String username, String rawPassword, String fullName, String email, String userType) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(rawPassword); // Store password in plain text
		user.setFullName(fullName);
		user.setEmail(email);
		user.setEnabled(true);
		Long id = users.create(user);

		// Set role based on user type
		if ("librarian".equals(userType)) {
			users.addAuthority(username, "ROLE_LIBRARIAN");
		} else {
			users.addAuthority(username, "ROLE_STUDENT");
		}

		return id;
	}
}




