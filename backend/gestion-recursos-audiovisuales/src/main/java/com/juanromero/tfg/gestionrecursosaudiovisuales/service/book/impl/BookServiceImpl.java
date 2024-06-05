package com.juanromero.tfg.gestionrecursosaudiovisuales.service.book.impl;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.book.Book;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.book.BookRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.book.BookService;


import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public String addBook(Book book) {
        Book existingBook = bookRepository.findByTitle(book.getTitle());

        if (existingBook == null) {
            bookRepository.save(book);
            return "Libro guardado con éxito";
        } else {
            return "No ha sido posible guardar el libro, título duplicado";
        }
    }

    @Override
    @Transactional
    public String deleteBook(Book book) {
        Book existingBook = bookRepository.findByTitle(book.getTitle());
        if (existingBook != null) {
            bookRepository.delete(existingBook);
            // Intentar cargar el libro borrado nuevamente
            Book deletedBook = bookRepository.findByTitle(book.getTitle());
            if (deletedBook == null) {
                return "El libro se ha borrado correctamente.";
            } else {
                return "Error: El libro no se ha podido borrar.";
            }
        } else {
            return "Error: El libro no existe.";
        }
    }

    @Override
    @Transactional
    public String updateBook(Book book) {
        Optional<Book> existingBook = bookRepository.findById(book.getId());
        if (existingBook.isPresent()) {
            bookRepository.save(book);
            return "El libro se ha actualizado correctamente.";
        } else {
            return "Error: El libro no existe.";
        }
    }

    @Override
    public List<Book> findAllBooks() {
        Iterable<Book> allBooks = bookRepository.findAll();
        if (allBooks != null) {
            List<Book> lista = new ArrayList<>();
            for (Book elemento : allBooks) {
                lista.add(elemento);
            }
            if (lista.size() > 0) {
                return lista;
            }
        }
        return null;
    }

	@Override
	public Book findByTitle(String title) {
        Book book = bookRepository.findByTitle(title);
        return book;
    }

	@Override
	public List<Book> findByPublishDate(String publishDate) {
        LocalDate date = LocalDate.parse(publishDate);
        List<Book> books = bookRepository.findByPublishDate(date);
        return books.isEmpty() ? null : books;
    }

	@Override
	public List<Book> findByAuthor(String author) {
	      List<Book> books = bookRepository.findByAuthor(author);
	        return books.isEmpty() ? null : books;
	    }

	@Override
	public List<Book> findByGenre(String genre) {
	      List<Book> books = bookRepository.findByGenre(genre);
	        return books.isEmpty() ? null : books;
	    }
}

