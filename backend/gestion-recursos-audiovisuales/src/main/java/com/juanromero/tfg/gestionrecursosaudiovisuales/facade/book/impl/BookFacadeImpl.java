package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.book.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.book.BookRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.book.BookResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.book.Book;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.book.BookFacade;
import com.juanromero.tfg.gestionrecursosaudiovisuales.mapper.book.BookMapper;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.book.BookService;

import java.util.List;

@Service
public class BookFacadeImpl implements BookFacade {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookMapper bookMapper;

    private static final String response_guardado_ok = "Libro guardado con éxito";
    private static final String response_borrado_ok = "El libro se ha borrado correctamente.";
    private static final String response_actualizado_ok = "El libro se ha actualizado correctamente.";
    private static final String response_encontrado_ok = "Libros encontrados correctamente.";

    @Override
    public BookResponse addBook(BookRequest bookRequest) {
        BookResponse response = new BookResponse();
        String descripcionPeticion = "";

        Book book = bookMapper.dtoToEntity(bookRequest);
        descripcionPeticion = bookService.addBook(book);
        response.setDescripcionPeticion(descripcionPeticion);

        if (descripcionPeticion.equalsIgnoreCase(response_guardado_ok)) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }

    @Override
    public BookResponse deleteBook(BookRequest bookRequest) {
        BookResponse response = new BookResponse();
        String descripcionPeticion = "";

        Book book = bookMapper.dtoToEntity(bookRequest);
        descripcionPeticion = bookService.deleteBook(book);
        response.setDescripcionPeticion(descripcionPeticion);

        if (descripcionPeticion.equalsIgnoreCase(response_borrado_ok)) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }

    @Override
    public BookResponse updateBook(BookRequest bookRequest) {
        BookResponse response = new BookResponse();
        String descripcionPeticion = "";

        Book book = bookMapper.dtoToEntity(bookRequest);
        descripcionPeticion = bookService.updateBook(book);
        response.setDescripcionPeticion(descripcionPeticion);

        if (descripcionPeticion.equalsIgnoreCase(response_actualizado_ok)) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }

    @Override
    public BookResponse findBooks() {
        BookResponse response = new BookResponse();
        String descripcionPeticion = "No se han encontrado libros";

        List<Book> lista = bookService.findAllBooks();
        response.setListaLibros(lista);
        if (lista.isEmpty()) {
            response.setEstadoPeticion("KO");
            response.setDescripcionPeticion(descripcionPeticion);
        } else {
            response.setEstadoPeticion("OK");
            response.setDescripcionPeticion(response_encontrado_ok);
        }

        return response;
    }
}

