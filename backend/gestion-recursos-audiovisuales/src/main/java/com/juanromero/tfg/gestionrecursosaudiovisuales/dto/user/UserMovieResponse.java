package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user;

import java.util.List;

public class UserMovieResponse {

	private String estadoPeticion;
	private String descripcionPeticion;
	private List<UserMovieRequest> listaUserMovies;

	public UserMovieResponse(String estadoPeticion, String descripcionPeticion,
			List<UserMovieRequest> listaUserMovies) {
		this.estadoPeticion = estadoPeticion;
		this.descripcionPeticion = descripcionPeticion;
		this.listaUserMovies = listaUserMovies;
	}

	public UserMovieResponse() {
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

	public List<UserMovieRequest> getListaUserMovies() {
		return listaUserMovies;
	}

	public void setListaUserMovies(List<UserMovieRequest> listaUserMovies) {
		this.listaUserMovies = listaUserMovies;
	}

}
