package com.juanromero.tfg.gestionrecursosaudiovisuales.mapper;

import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;

@Service
public class UserMapper {
	public User dtoToEntity(UserRequest dto) {
		User entity = new User();
		
		entity.setId(dto.getId());
		entity.setUsername(dto.getUsername());
		entity.setNombre(dto.getNombre());
		entity.setBio(dto.getBio());
		entity.setPassword(dto.getPassword());
		entity.setEmail(dto.getEmail());
		entity.setRol(dto.getRol());
		return entity;
	}
}
