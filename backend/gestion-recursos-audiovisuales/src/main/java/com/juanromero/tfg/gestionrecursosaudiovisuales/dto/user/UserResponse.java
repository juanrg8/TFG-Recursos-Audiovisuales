package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user;

import java.util.List;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
	
	private String estadoPeticion;
	private String descripcionPeticion;
	private List<User> listaUsuarios;
	
	public UserResponse(String estadoPeticion, String descripcionPeticion, List<User> listaUsuarios) {
		super();
		this.estadoPeticion = estadoPeticion;
		this.descripcionPeticion = descripcionPeticion;
		this.listaUsuarios = listaUsuarios;
	}


	public UserResponse() {
		
	}

}
