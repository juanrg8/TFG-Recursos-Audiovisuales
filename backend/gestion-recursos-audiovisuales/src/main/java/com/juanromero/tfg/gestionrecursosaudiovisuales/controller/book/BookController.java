package com.juanromero.tfg.gestionrecursosaudiovisuales.controller.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.book.BookRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.book.BookResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.book.BookFacade;

 

@RestController
@RequestMapping(path="/book")
public class BookController {

    @Autowired
    private BookFacade bookFacade;

    @PostMapping(path="/addbook")
    public BookResponse addBook(@RequestBody BookRequest request) {
        return bookFacade.addBook(request);
    }

    @DeleteMapping(path="/deletebook")
    public BookResponse deleteBook(@RequestBody BookRequest request) {
        return bookFacade.deleteBook(request);
    }

    @PutMapping(path="/updatebook")
    public BookResponse updateBook(@RequestBody BookRequest request) {
        return bookFacade.updateBook(request);
    }

    @GetMapping(path="/findbooks")
    public BookResponse findAllBooks() {
        return bookFacade.findBooks();
    }
}
