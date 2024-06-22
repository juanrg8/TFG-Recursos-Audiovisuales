package com.juanromero.tfg.gestionrecursosaudiovisuales.controller.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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

    private final AuthenticationManager authenticationManager;
    private final UserDetailServiceImpl userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserDetailServiceImpl userDetailsService,
                          JwtTokenUtil jwtTokenUtil, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest) {
        // Validar campos obligatorios
        if (authenticationRequest.getUsername() == null || authenticationRequest.getUsername().isEmpty() ||
            authenticationRequest.getPassword() == null || authenticationRequest.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Campos obligatorios vacíos");
        }

        try {
            // Intenta autenticar al usuario
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
                )
            );

            // Obtiene los detalles del usuario autenticado
            UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

            // Genera el token JWT
            final String token = jwtTokenUtil.generateToken(userDetails);

            // Devuelve la respuesta con el token JWT
            return ResponseEntity.ok(new AuthResponse(token, authenticationRequest.getUsername()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AuthRequest registrationRequest) {
        // Validar campos obligatorios
        if (registrationRequest.getUsername() == null || registrationRequest.getUsername().isEmpty() ||
            registrationRequest.getPassword() == null || registrationRequest.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Campos obligatorios vacíos");
        }

        Optional<User> usuario = userRepository.findByUsername(registrationRequest.getUsername());
        if (usuario.isPresent()) {
            return new ResponseEntity<>("El nombre de usuario ya está en uso", HttpStatus.BAD_REQUEST);
        }

        String encodedPassword = passwordEncoder.encode(registrationRequest.getPassword());

        User newUser = new User();
        newUser.setUsername(registrationRequest.getUsername());
        newUser.setPassword(encodedPassword);
        newUser.setRol(Rol.USER);
        newUser.setBio(registrationRequest.getBio());
        newUser.setEmail(registrationRequest.getEmail());
        newUser.setNombre(registrationRequest.getName());

        userRepository.save(newUser);

        return new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.CREATED);
    }



}
