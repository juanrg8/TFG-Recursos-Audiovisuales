package com.juanromero.tfg.gestionrecursosaudiovisuales.controller.book;

import java.util.List;

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
        return bookFacade.findAllBooks();
    }
    
    @GetMapping(path="/title")
    public List<BookRequest> findBooksByTitle(@RequestBody BookRequest request) {
        return bookFacade.findByTitle(request.getTitle()).getListaLibros();
    }
    
    @GetMapping(path="/genre")
    public List<BookRequest> findBooksByGenre(@RequestBody BookRequest request) {
        return bookFacade.findByGenre(request.getGenre()).getListaLibros();
    }
    
    @GetMapping(path="/author")
    public List<BookRequest> findBooksByArtist(@RequestBody BookRequest request) {
        return bookFacade.findByAuthor(request.getAuthor()).getListaLibros();
    }
    
    @GetMapping(path="/date")
    public List<BookRequest> findBooksByPublishDate(@RequestBody BookRequest request) {
        return bookFacade.findByPublishDate(request.getPublishDate().toString()).getListaLibros();
    }
    
}
