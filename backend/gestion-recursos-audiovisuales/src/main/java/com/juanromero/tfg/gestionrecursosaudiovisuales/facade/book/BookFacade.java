package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.book;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.book.BookRequest; 
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.book.BookResponse;

public interface BookFacade {
    
    BookResponse addBook(BookRequest bookRequest);
    
    BookResponse deleteBook(BookRequest bookRequest);
    
    BookResponse updateBook(BookRequest bookRequest);
    
    BookResponse findBooks();
}

