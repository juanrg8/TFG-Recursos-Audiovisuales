package com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.PasswordRestoreService;

@Service
public class PasswordRestoreServiceImpl implements PasswordRestoreService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void resetPassword(String userEmail) {
        String newPassword = generateRandomPassword();

        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("User not found with email: " + userEmail);
        }

        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        sendEmail(userEmail, newPassword);
    }

    private String generateRandomPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private void sendEmail(String userEmail, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Recuperación de contraseña");
        message.setText("Tu nueva contraseña es: " + newPassword);

        emailSender.send(message);
    }
}

