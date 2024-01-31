package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.test.TestRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.test.TestResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.TestFacade;
import com.juanromero.tfg.gestionrecursosaudiovisuales.mapper.UserMapper;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.UserService;

@Service
public class TestFacadeImpl implements TestFacade{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMapper userMapper;
	
	private static final String response_guardado_ok = "Usuario guardado con exito";
	
	
	@Override
	public TestResponse testmethod(TestRequest test) {
		
		TestResponse response = new TestResponse();
		String descripcionPeticion= "";
		
	
		User usuario = userMapper.dtoToEntity(test);
		descripcionPeticion = userService.addUser(usuario);
		response.setDescripcionPeticion(descripcionPeticion);
		
		if(descripcionPeticion.equalsIgnoreCase(response_guardado_ok)) {
			response.setEstadoPeticion("OK");
		}else {
			response.setEstadoPeticion("KO");
		}
		return response;
	}

}
