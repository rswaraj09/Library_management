package com.example.library.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserRepository {

	private final JdbcTemplate jdbc;

	public UserRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public Optional<User> findByUsername(String username) {
		try {
			User user = jdbc.queryForObject(
				"SELECT id, username, password, full_name AS fullName, email, enabled FROM users WHERE username = ?",
				new BeanPropertyRowMapper<>(User.class), username);
			return Optional.ofNullable(user);
		} catch (EmptyResultDataAccessException ex) {
			return Optional.empty();
		}
	}

	public Optional<User> findByEmail(String email) {
		try {
			User user = jdbc.queryForObject(
				"SELECT id, username, password, full_name AS fullName, email, enabled FROM users WHERE email = ?",
				new BeanPropertyRowMapper<>(User.class), email);
			return Optional.ofNullable(user);
		} catch (EmptyResultDataAccessException ex) {
			return Optional.empty();
		}
	}

	public List<String> findAuthorities(String username) {
		return jdbc.queryForList("SELECT authority FROM authorities WHERE username = ?", String.class, username);
	}

	public Long create(User user) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(con -> {
			PreparedStatement ps = con.prepareStatement(
				"INSERT INTO users(username, password, full_name, email, enabled) VALUES (?,?,?,?,?)",
				Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, StringUtils.hasText(user.getFullName()) ? user.getFullName() : null);
			ps.setString(4, StringUtils.hasText(user.getEmail()) ? user.getEmail() : null);
			ps.setBoolean(5, user.isEnabled());
			return ps;
		}, keyHolder);
		Number key = keyHolder.getKey();
		return key == null ? null : key.longValue();
	}

	public void addAuthority(String username, String authority) {
		jdbc.update("INSERT INTO authorities(username, authority) VALUES (?,?)", username, authority);
	}
}




