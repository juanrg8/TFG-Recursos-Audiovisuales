package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.test;

public class TestResponse {
	
	private String estadoPeticion;
	private String descripcionPeticion;
	
	
	public TestResponse(String estadoPeticion, String descripcionPeticion) {
		super();
		this.estadoPeticion = estadoPeticion;
		this.descripcionPeticion = descripcionPeticion;
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
	
	

}
