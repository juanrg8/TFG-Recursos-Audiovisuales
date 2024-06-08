package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user;

import java.util.List;

public class UserBookResponse {

	private String estadoPeticion;
	private String descripcionPeticion;
	private List<UserBookRequest> listaUserBooks;

	public UserBookResponse(String estadoPeticion, String descripcionPeticion, List<UserBookRequest> listaUserBooks) {
		this.estadoPeticion = estadoPeticion;
		this.descripcionPeticion = descripcionPeticion;
		this.listaUserBooks = listaUserBooks;
	}

	public UserBookResponse() {
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

	public List<UserBookRequest> getListaUserBooks() {
		return listaUserBooks;
	}

	public void setListaUserBooks(List<UserBookRequest> listaUserBooks) {
		this.listaUserBooks = listaUserBooks;
	}

}
