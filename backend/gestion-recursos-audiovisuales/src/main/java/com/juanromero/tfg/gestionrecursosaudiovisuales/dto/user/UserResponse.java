package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user;

import java.util.List; 


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
	
	private String estadoPeticion;
	private String descripcionPeticion;
	private List<UserRequest> listaUsuarios;
	private UserRequest usuario;
	
	public UserResponse(String estadoPeticion, String descripcionPeticion, List<UserRequest> listaUsuarios,UserRequest usuario) {
		super();
		this.estadoPeticion = estadoPeticion;
		this.descripcionPeticion = descripcionPeticion;
		this.listaUsuarios = listaUsuarios;
		this.usuario = usuario;
	}


	public UserResponse() {
		
	}

}
