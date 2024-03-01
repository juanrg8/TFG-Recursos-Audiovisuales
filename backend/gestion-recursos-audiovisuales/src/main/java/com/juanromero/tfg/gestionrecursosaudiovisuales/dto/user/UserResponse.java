package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user;

import java.util.List;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;

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


	public String getEstadoPeticion() {
		return estadoPeticion;
	}


	public void setEstadoPeticion(String estadoPeticion) {
		this.estadoPeticion = estadoPeticion;
	}


	public String getDescripcionPeticion() {
		return descripcionPeticion;
	}


	public void setDescripcionPeticion(String descripcionPeticion) {
		this.descripcionPeticion = descripcionPeticion;
	}


	public List<User> getListaUsuarios() {
		return listaUsuarios;
	}


	public void setListaUsuarios(List<User> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
	
	

}
