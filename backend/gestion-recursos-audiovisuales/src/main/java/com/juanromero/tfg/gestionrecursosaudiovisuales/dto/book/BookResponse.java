package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.book;

import java.util.List;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.book.Book;

public class BookResponse {

	private String estadoPeticion;
	private String descripcionPeticion;
	private List<Book> listaLibros;

	public BookResponse(String estadoPeticion, String descripcionPeticion, List<Book> listaLibros) {
		super();
		this.estadoPeticion = estadoPeticion;
		this.descripcionPeticion = descripcionPeticion;
		this.listaLibros = listaLibros;
	}

	public BookResponse() {

	}

	// Getters y setters
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

	public List<Book> getListaLibros() {
		return listaLibros;
	}

	public void setListaLibros(List<Book> listaLibros) {
		this.listaLibros = listaLibros;
	}
}
