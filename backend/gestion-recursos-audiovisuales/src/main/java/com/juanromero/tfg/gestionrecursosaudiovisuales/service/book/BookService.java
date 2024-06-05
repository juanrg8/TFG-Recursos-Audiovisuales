package com.juanromero.tfg.gestionrecursosaudiovisuales.service.book;

import java.util.List;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.book.Book;

public interface BookService {
    
    String addBook(Book book);
    
    String deleteBook(Book book);
    
    String updateBook(Book book);
    
    List<Book> findAllBooks();
    
    Book findByTitle(String title);
    
    List<Book> findByPublishDate(String publishDate);
    
    List<Book> findByAuthor(String author);
    
    List<Book> findByGenre(String genre);
}

