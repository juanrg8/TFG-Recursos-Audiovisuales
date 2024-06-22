package com.juanromero.tfg.gestionrecursosaudiovisuales.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.juanromero.tfg.gestionrecursosaudiovisuales.config.JwtTokenUtil;
import com.juanromero.tfg.gestionrecursosaudiovisuales.controller.auth.AuthController;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.auth.AuthRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.auth.AuthResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.impl.UserDetailServiceImpl;

class AuthControllerTest {

	@InjectMocks
	private AuthController authController;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private UserDetailServiceImpl userDetailsService;

	@Mock
	private JwtTokenUtil jwtTokenUtil;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private UserRepository userRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateAuthenticationToken() {
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername("testuser");
		authRequest.setPassword("password");

		UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername("testuser")
				.password("password").authorities("USER").build();

		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
		when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
		when(jwtTokenUtil.generateToken(any(UserDetails.class))).thenReturn("token");

		ResponseEntity<?> response = authController.createAuthenticationToken(authRequest);

		verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
		verify(userDetailsService).loadUserByUsername(anyString());
		verify(jwtTokenUtil).generateToken(any(UserDetails.class));

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertTrue(response.getBody() instanceof AuthResponse);
		AuthResponse authResponse = (AuthResponse) response.getBody();
		assertEquals("token", authResponse.getToken());
		assertEquals("testuser", authResponse.getUsername());
	}

	@Test
	void testCreateAuthenticationTokenWithBadCredentials() {
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername("testuser");
		authRequest.setPassword("wrongpassword");

		doThrow(new BadCredentialsException("Bad credentials")).when(authenticationManager)
				.authenticate(any(UsernamePasswordAuthenticationToken.class));

		ResponseEntity<?> response = authController.createAuthenticationToken(authRequest);

		verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertEquals("Credenciales incorrectas", response.getBody());
	}

	@Test
	void testRegisterUserSuccess() {
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername("newuser");
		authRequest.setPassword("password");
		authRequest.setBio("bio");
		authRequest.setEmail("newuser@example.com");
		authRequest.setName("New User");

		when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
		when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
		when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

		ResponseEntity<String> response = authController.registerUser(authRequest);

		verify(userRepository).findByUsername(anyString());
		verify(passwordEncoder).encode(anyString());
		verify(userRepository).save(any(User.class));

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("Usuario registrado exitosamente", response.getBody());
	}

	@Test
	void testRegisterUserUsernameExists() {
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername("existinguser");
		authRequest.setPassword("password");

		User existingUser = new User();
		existingUser.setUsername("existinguser");

		when(userRepository.findByUsername("existinguser")).thenReturn(Optional.of(existingUser));

		ResponseEntity<String> response = authController.registerUser(authRequest);

		verify(userRepository, times(1)).findByUsername("existinguser");

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("El nombre de usuario ya está en uso", response.getBody());
	}

	@Test
	void testRegisterUserWithEmptyFields() {
	    AuthRequest authRequest = new AuthRequest();
	    authRequest.setUsername("");
	    authRequest.setPassword("");

	    ResponseEntity<String> response = authController.registerUser(authRequest);

	    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	    assertEquals("Campos obligatorios vacíos", response.getBody());
	}


	@Test
	void testRegisterUserWithNullFields() {
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername(null);
		authRequest.setPassword(null);

		ResponseEntity<String> response = authController.registerUser(authRequest);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	void testCreateAuthenticationTokenWithEmptyFields() {
	    AuthRequest authRequest = new AuthRequest();
	    authRequest.setUsername("");
	    authRequest.setPassword("");

	    ResponseEntity<?> response = authController.createAuthenticationToken(authRequest);

	    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	    assertEquals("Campos obligatorios vacíos", response.getBody());
	}

}
