package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class AuthRequest {

	@NotNull(message = "Username cannot be null")
	@NotEmpty(message = "Username cannot be empty")
	private String username;

	@NotNull(message = "Password cannot be null")
	@NotEmpty(message = "Password cannot be empty")
	private String password;
	private String bio;
	private String email;
	private String name;

	public AuthRequest() {
	}

	public AuthRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public AuthRequest(String username, String password, String bio, String email, String name) {
		this.username = username;
		this.password = password;
		this.bio = bio;
		this.email = email;
		this.name = name;
	}

	// Getters y setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
