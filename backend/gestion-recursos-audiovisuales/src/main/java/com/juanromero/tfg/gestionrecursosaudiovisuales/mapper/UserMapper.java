package com.juanromero.tfg.gestionrecursosaudiovisuales.mapper;

import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.test.TestRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;

@Service
public class UserMapper {
	public User dtoToEntity(TestRequest dto) {
		User entity = new User();
		
		entity.setUsername(dto.getUsername());
		entity.setPassword(dto.getPassword());
		entity.setEmail(dto.getEmail());
		return entity;
	}
}
