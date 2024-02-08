package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.test;

import java.util.List;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;

public class TestResponse {
	
	private String estadoPeticion;
	private String descripcionPeticion;
	private List<User> listaUsuarios;
	
	public TestResponse(String estadoPeticion, String descripcionPeticion, List<User> listaUsuarios) {
		super();
		this.estadoPeticion = estadoPeticion;
		this.descripcionPeticion = descripcionPeticion;
		this.listaUsuarios = listaUsuarios;
	}


	public TestResponse() {
		
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
