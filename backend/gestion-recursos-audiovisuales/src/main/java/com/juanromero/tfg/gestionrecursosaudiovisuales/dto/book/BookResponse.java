package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.book;

import java.util.List;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.book.Book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
