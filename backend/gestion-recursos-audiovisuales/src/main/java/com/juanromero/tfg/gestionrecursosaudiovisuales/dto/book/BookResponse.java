package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.book;

import java.util.List; 


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookResponse {

	private String estadoPeticion;
	private String descripcionPeticion;
	private List<BookRequest> listaLibros;

	public BookResponse(String estadoPeticion, String descripcionPeticion, List<BookRequest> listaLibros) {
		super();
		this.estadoPeticion = estadoPeticion;
		this.descripcionPeticion = descripcionPeticion;
		this.listaLibros = listaLibros;
	}

	public BookResponse() {

	}

}
