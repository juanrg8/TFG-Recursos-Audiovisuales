package com.juanromero.tfg.gestionrecursosaudiovisuales.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.TestFacade;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.UserRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public String addUser(User user) {
		Optional<User> posibleUser = userRepository.findById(user.getUsername());

		if(!posibleUser.isPresent()) {
			userRepository.save(user);
			return "Usuario guardado con exito";
		} else {
			return "No ha sido posible guardar el usuario, usuario duplicado";
		}
		
		
	}
	
	
	
}
