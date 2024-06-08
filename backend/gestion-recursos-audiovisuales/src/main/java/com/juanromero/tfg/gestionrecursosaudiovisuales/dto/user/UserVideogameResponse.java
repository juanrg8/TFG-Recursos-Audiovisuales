package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user;

import java.util.List;

public class UserVideogameResponse {

    private String estadoPeticion;
    private String descripcionPeticion;
    private List<UserVideogameRequest> listaUserVideogames;

    public UserVideogameResponse(String estadoPeticion, String descripcionPeticion, List<UserVideogameRequest> listaUserVideogames) {
        this.estadoPeticion = estadoPeticion;
        this.descripcionPeticion = descripcionPeticion;
        this.listaUserVideogames = listaUserVideogames;
    }

    public UserVideogameResponse() {
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

	public List<UserVideogameRequest> getListaUserVideogames() {
		return listaUserVideogames;
	}

	public void setListaUserVideogames(List<UserVideogameRequest> listaUserVideogames) {
		this.listaUserVideogames = listaUserVideogames;
	}

    // Getters y setters
}
