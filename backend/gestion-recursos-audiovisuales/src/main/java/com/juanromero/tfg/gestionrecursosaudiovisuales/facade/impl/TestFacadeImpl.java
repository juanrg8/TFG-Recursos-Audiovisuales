package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.test.TestRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.test.TestResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.TestFacade;
import com.juanromero.tfg.gestionrecursosaudiovisuales.mapper.UserMapper;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.UserService;

@Service
public class TestFacadeImpl implements TestFacade {

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper userMapper;

	private static final String response_guardado_ok = "Usuario guardado con exito";
	private static final String response_borrado_ok = "El usuario se ha borrado correctamente.";
	private static final String response_actualizado_ok = "El usuario se ha actualizado correctamente.";
	private static final String response_encontrado_ok = "El usuario se ha encontrado correctamente.";

	@Override
	public TestResponse addUser(TestRequest test) {

		TestResponse response = new TestResponse();
		String descripcionPeticion = "";

		User usuario = userMapper.dtoToEntity(test);
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
	public TestResponse deleteUser(TestRequest test) {

		TestResponse response = new TestResponse();
		String descripcionPeticion = "";

		User usuario = userMapper.dtoToEntity(test);
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
	public TestResponse updateUser(TestRequest test) {

		TestResponse response = new TestResponse();
		String descripcionPeticion = "";

		User usuario = userMapper.dtoToEntity(test);
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
	public TestResponse findUser() {
		TestResponse response = new TestResponse();

		List<User> lista = userService.findAllUsers();
		response.setListaUsuarios(lista);
		//Falta devolver estado peticiones
		return response;
	}

}
