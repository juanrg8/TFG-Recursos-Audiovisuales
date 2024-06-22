package com.juanromero.tfg.gestionrecursosaudiovisuales.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.juanromero.tfg.gestionrecursosaudiovisuales.controller.book.BookController;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.book.BookRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.book.BookResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.book.BookFacade;

public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookFacade bookFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddBook() {
        BookRequest request = new BookRequest(1, "Test Book", 200, LocalDate.now(), "Test Author", "Fiction");
        BookResponse expectedResponse = new BookResponse("success", "Book added successfully", Collections.singletonList(request));

        when(bookFacade.addBook(any(BookRequest.class))).thenReturn(expectedResponse);

        BookResponse response = bookController.addBook(request);

        assertEquals(expectedResponse, response);
        verify(bookFacade, times(1)).addBook(any(BookRequest.class));
    }
    
    @Test
    void testDeleteBook() {
        BookRequest request = new BookRequest(1, "Test Book", 200, LocalDate.now(), "Test Author", "Fiction");
        BookResponse expectedResponse = new BookResponse("success", "Book deleted successfully", Collections.singletonList(request));

        when(bookFacade.deleteBook(any(BookRequest.class))).thenReturn(expectedResponse);

        BookResponse response = bookController.deleteBook(request);

        assertEquals(expectedResponse, response);
        verify(bookFacade, times(1)).deleteBook(any(BookRequest.class));
    }

    @Test
    void testUpdateBook() {
        BookRequest request = new BookRequest(1, "Test Book", 200, LocalDate.now(), "Test Author", "Fiction");
        BookResponse expectedResponse = new BookResponse("success", "Book updated successfully", Collections.singletonList(request));

        when(bookFacade.updateBook(any(BookRequest.class))).thenReturn(expectedResponse);

        BookResponse response = bookController.updateBook(request);

        assertEquals(expectedResponse, response);
        verify(bookFacade, times(1)).updateBook(any(BookRequest.class));
    }

    @Test
    void testFindAllBooks() {
        List<BookRequest> bookList = Collections.singletonList(
                new BookRequest(1, "Test Book", 200, LocalDate.now(), "Test Author", "Fiction")
        );
        BookResponse expectedResponse = new BookResponse("success", "Books found successfully", bookList);

        when(bookFacade.findAllBooks()).thenReturn(expectedResponse);

        BookResponse response = bookController.findAllBooks();

        assertEquals(expectedResponse, response);
        assertEquals(bookList.size(), response.getListaLibros().size());
        assertEquals(expectedResponse.getListaLibros(), response.getListaLibros());
        verify(bookFacade, times(1)).findAllBooks();
    }
    

    @Test
    void testFindBooksByTitle() {
        String title = "Test Book";
        BookRequest request = new BookRequest();
        request.setTitle(title);

        List<BookRequest> bookList = Collections.singletonList(
                new BookRequest(1, title, 200, LocalDate.now(), "Test Author", "Fiction")
        );

        when(bookFacade.findByTitle(title)).thenReturn(new BookResponse("success", "Books found successfully", bookList));

        List<BookRequest> response = bookController.findBooksByTitle(request);

        assertEquals(bookList.size(), response.size());
        assertEquals(bookList, response);
        verify(bookFacade, times(1)).findByTitle(title);
    }

    @Test
    void testFindBooksByGenre() {
        String genre = "Fiction";
        BookRequest request = new BookRequest();
        request.setGenre(genre);

        List<BookRequest> bookList = Collections.singletonList(
                new BookRequest(1, "Test Book", 200, LocalDate.now(), "Test Author", genre)
        );

        when(bookFacade.findByGenre(genre)).thenReturn(new BookResponse("success", "Books found successfully", bookList));

        List<BookRequest> response = bookController.findBooksByGenre(request);

        assertEquals(bookList.size(), response.size());
        assertEquals(bookList, response);
        verify(bookFacade, times(1)).findByGenre(genre);
    }

    @Test
    void testFindBooksByAuthor() {
        String author = "Test Author";
        BookRequest request = new BookRequest();
        request.setAuthor(author);

        List<BookRequest> bookList = Collections.singletonList(
                new BookRequest(1, "Test Book", 200, LocalDate.now(), author, "Fiction")
        );

        when(bookFacade.findByAuthor(author)).thenReturn(new BookResponse("success", "Books found successfully", bookList));

        List<BookRequest> response = bookController.findBooksByAuthor(request);

        assertEquals(bookList.size(), response.size());
        assertEquals(bookList, response);
        verify(bookFacade, times(1)).findByAuthor(author);
    }

    @Test
    void testFindBooksByPublishDate() {
        LocalDate publishDate = LocalDate.now();
        BookRequest request = new BookRequest();
        request.setPublishDate(publishDate);

        List<BookRequest> bookList = Collections.singletonList(
                new BookRequest(1, "Test Book", 200, publishDate, "Test Author", "Fiction")
        );

        when(bookFacade.findByPublishDate(publishDate.toString())).thenReturn(new BookResponse("success", "Books found successfully", bookList));

        List<BookRequest> response = bookController.findBooksByPublishDate(request);

        assertEquals(bookList.size(), response.size());
        assertEquals(bookList, response);
        verify(bookFacade, times(1)).findByPublishDate(publishDate.toString());
    }
	
}
