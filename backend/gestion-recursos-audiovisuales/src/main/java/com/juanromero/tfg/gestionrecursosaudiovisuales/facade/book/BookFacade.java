package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.book;


import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.book.BookRequest; 
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.book.BookResponse;

public interface BookFacade {
    
    BookResponse addBook(BookRequest bookRequest);
    
    BookResponse deleteBook(BookRequest bookRequest);
    
    BookResponse updateBook(BookRequest bookRequest);
    
    BookResponse findAllBooks();
    
    BookResponse findByTitle(String title);
    
    BookResponse findByPublishDate(String publishDate);
    
    BookResponse findByAuthor(String author);
    
    BookResponse findByGenre(String genre);
}

