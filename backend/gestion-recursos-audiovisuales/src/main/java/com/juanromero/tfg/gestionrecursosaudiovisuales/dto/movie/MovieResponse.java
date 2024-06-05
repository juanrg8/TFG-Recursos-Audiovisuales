package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.movie;
 
import java.util.List; 


public class MovieResponse {

    private String estadoPeticion;
    private String descripcionPeticion;
    private List<MovieRequest> listaPeliculas;

    public MovieResponse(String estadoPeticion, String descripcionPeticion, List<MovieRequest> listaPeliculas) {
        this.estadoPeticion = estadoPeticion;
        this.descripcionPeticion = descripcionPeticion;
        this.listaPeliculas = listaPeliculas;
    }

    public MovieResponse() {

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

	public List<MovieRequest> getListaPeliculas() {
		return listaPeliculas;
	}

	public void setListaPeliculas(List<MovieRequest> listaPeliculas) {
		this.listaPeliculas = listaPeliculas;
	}
    
    
	
}
