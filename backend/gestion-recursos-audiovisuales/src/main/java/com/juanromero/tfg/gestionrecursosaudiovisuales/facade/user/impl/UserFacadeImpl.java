package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user.impl;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user.UserFacade;
import com.juanromero.tfg.gestionrecursosaudiovisuales.mapper.user.UserMapper;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.UserService;

@Service
public class UserFacadeImpl implements UserFacade {

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper userMapper;

	private static final String response_guardado_ok = "Usuario guardado con exito";
	private static final String response_borrado_ok = "El usuario se ha borrado correctamente.";
	private static final String response_actualizado_ok = "El usuario se ha actualizado correctamente.";
	private static final String response_encontrado_ok = "Usuarios encontrados correctamente.";

	@Override
	public UserResponse addUser(UserRequest userRequest) {

		UserResponse response = new UserResponse();
		String descripcionPeticion = "";

		User usuario = userMapper.dtoToEntity(userRequest);
		descripcionPeticion = userService.addUser(usuario);
		response.setDescripcionPeticion(descripcionPeticion);

		if (descripcionPeticion.equalsIgnoreCase(response_guardado_ok)) {
			response.setEstadoPeticion("OK");
		} else {
			response.setEstadoPeticion("KO");
		}
		return response;
	}

	@Override
	public UserResponse deleteUser(UserRequest userRequest) {

		UserResponse response = new UserResponse();
		String descripcionPeticion = "";

		User usuario = userMapper.dtoToEntity(userRequest);
		descripcionPeticion = userService.deleteUser(usuario);
		response.setDescripcionPeticion(descripcionPeticion);

		if (descripcionPeticion.equalsIgnoreCase(response_borrado_ok)) {
			response.setEstadoPeticion("OK");
		} else {
			response.setEstadoPeticion("KO");
		}
		return response;
	}

	@Override
	public UserResponse updateUser(UserRequest userRequest) {

		UserResponse response = new UserResponse();
		String descripcionPeticion = "";

		User usuario = userMapper.dtoToEntity(userRequest);
		descripcionPeticion = userService.updateUser(usuario);
		response.setDescripcionPeticion(descripcionPeticion);

		if (descripcionPeticion.equalsIgnoreCase(response_actualizado_ok)) {
			response.setEstadoPeticion("OK");
		} else {
			response.setEstadoPeticion("KO");
		}
		return response;
	}

	@Override
	public UserResponse findUser() {
		UserResponse response = new UserResponse();
		String descripcionPeticion = "No se han encontrado usuarios";
		
		List<User> lista = userService.findAllUsers();
		response.setListaUsuarios(lista);
		if(lista.isEmpty()) {
			response.setEstadoPeticion("KO");
			response.setDescripcionPeticion(descripcionPeticion);
		} else {
			response.setEstadoPeticion("OK");
			response.setDescripcionPeticion(response_encontrado_ok);
		}
		
		return response;
	}

}
