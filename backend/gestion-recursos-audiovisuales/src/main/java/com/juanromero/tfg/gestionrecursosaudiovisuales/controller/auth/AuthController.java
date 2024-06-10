package com.juanromero.tfg.gestionrecursosaudiovisuales.controller.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juanromero.tfg.gestionrecursosaudiovisuales.config.JwtTokenUtil;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.auth.AuthRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.auth.AuthResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.Rol;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.impl.UserDetailServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailServiceImpl userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private final UserRepository userRepository;

	public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest) {
		// Intenta autenticar al usuario
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
				authenticationRequest.getPassword()));

		// Obtiene los detalles del usuario autenticado
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		// Genera el token JWT
		final String token = jwtTokenUtil.generateToken(userDetails);

		// Devuelve la respuesta con el token JWT
		return ResponseEntity.ok(new AuthResponse(token));
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody AuthRequest registrationRequest) {
		// Verifica si el nombre de usuario ya está en uso
		Optional<User> usuario = userRepository.findByUsername(registrationRequest.getUsername());
		if (usuario.isPresent()) {
			return new ResponseEntity<>("El nombre de usuario ya está en uso", HttpStatus.BAD_REQUEST);
		}

		// Codifica la contraseña antes de guardarla en la base de datos
		String encodedPassword = passwordEncoder.encode(registrationRequest.getPassword());

		// Crea un nuevo usuario
		User newUser = new User();
		newUser.setUsername(registrationRequest.getUsername());
		newUser.setPassword(encodedPassword);
		// Establece el rol del usuario como "USER"
		newUser.setRol(Rol.USER);

		newUser.setBio(registrationRequest.getBio());

		newUser.setEmail(registrationRequest.getEmail());
		
		newUser.setNombre(registrationRequest.getName());

		// Guarda el nuevo usuario en la base de datos
		userRepository.save(newUser);

		return new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.CREATED);
	}
}
