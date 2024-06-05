package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user;

import java.util.List;  




public class UserAlbumResponse {
	
	private String estadoPeticion;
	private String descripcionPeticion;
	private List<UserAlbumRequest> listaUserAlbums;
	
	public UserAlbumResponse(String estadoPeticion, String descripcionPeticion, List<UserAlbumRequest> listaUserAlbums) {
		super();
		this.estadoPeticion = estadoPeticion;
		this.descripcionPeticion = descripcionPeticion;
		this.listaUserAlbums = listaUserAlbums;
	}
	
	public UserAlbumResponse() {
		
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

	public List<UserAlbumRequest> getListaUserAlbums() {
		return listaUserAlbums;
	}

	public void setListaUserAlbums(List<UserAlbumRequest> listaUserAlbums) {
		this.listaUserAlbums = listaUserAlbums;
	}
	
	
}
