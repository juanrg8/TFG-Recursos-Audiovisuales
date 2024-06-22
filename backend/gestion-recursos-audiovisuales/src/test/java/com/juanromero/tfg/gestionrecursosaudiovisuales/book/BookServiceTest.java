package com.juanromero.tfg.gestionrecursosaudiovisuales.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.book.Book;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.book.BookRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.book.impl.BookServiceImpl;

public class BookServiceTest {
    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddBookSuccess() {
        Book bookToAdd = new Book();
        bookToAdd.setId(1);
        bookToAdd.setTitle("Test Book");
        bookToAdd.setPages(200);
        bookToAdd.setPublishDate(LocalDate.now());
        bookToAdd.setAuthor("Test Author");
        bookToAdd.setGenre("Fiction");

        when(bookRepository.findByTitle("Test Book")).thenReturn(null);
        when(bookRepository.save(any(Book.class))).thenReturn(bookToAdd);

        String result = bookService.addBook(bookToAdd);

        assertEquals("Libro guardado con éxito", result);
        verify(bookRepository, times(1)).findByTitle("Test Book");
        verify(bookRepository, times(1)).save(any(Book.class));
    }
    @Test
    void testAddBookDuplicateTitle() {
        Book existingBook = new Book();
        existingBook.setId(1);
        existingBook.setTitle("Test Book");
        existingBook.setPages(200);
        existingBook.setPublishDate(LocalDate.now());
        existingBook.setAuthor("Test Author");
        existingBook.setGenre("Fiction");

        when(bookRepository.findByTitle("Test Book")).thenReturn(existingBook);

        String result = bookService.addBook(existingBook);

        assertEquals("No ha sido posible guardar el libro, título duplicado", result);
        verify(bookRepository, times(1)).findByTitle("Test Book");
        verify(bookRepository, times(0)).save(any(Book.class));
    }
    
    @Test
    void testDeleteBookSuccess() {
        Book bookToDelete = new Book();
        bookToDelete.setId(1);
        bookToDelete.setTitle("Test Book");
        bookToDelete.setPages(200);
        bookToDelete.setPublishDate(LocalDate.now());
        bookToDelete.setAuthor("Test Author");
        bookToDelete.setGenre("Fiction");

        when(bookRepository.findByTitle("Test Book")).thenReturn(bookToDelete);

        bookService.deleteBook(bookToDelete);

        verify(bookRepository, times(2)).findByTitle("Test Book");

        verify(bookRepository, times(1)).delete(any(Book.class));

        when(bookRepository.findByTitle("Test Book")).thenReturn(null);

        String resultAfterDelete = bookService.deleteBook(bookToDelete);

        verify(bookRepository, times(3)).findByTitle("Test Book");


        assertEquals("Error: El libro no existe.", resultAfterDelete);
    }
    
    @Test
    void testDeleteBookNotFound() {
        Book bookToDelete = new Book();
        bookToDelete.setTitle("Nonexistent Book");

        when(bookRepository.findByTitle("Nonexistent Book")).thenReturn(null);

        String result = bookService.deleteBook(bookToDelete);

        assertEquals("Error: El libro no existe.", result);
        verify(bookRepository, times(1)).findByTitle("Nonexistent Book");
        verify(bookRepository, times(0)).delete(any(Book.class));
    }

    @Test
    void testUpdateBookSuccess() {
        Book existingBook = new Book();
        existingBook.setId(1);
        existingBook.setTitle("Test Book");
        existingBook.setPages(200);
        existingBook.setPublishDate(LocalDate.now());
        existingBook.setAuthor("Test Author");
        existingBook.setGenre("Fiction");

        Book updatedBook = new Book();
        updatedBook.setId(1);
        updatedBook.setTitle("Updated Book Title");
        updatedBook.setPages(250);
        updatedBook.setPublishDate(LocalDate.now());
        updatedBook.setAuthor("Updated Author");
        updatedBook.setGenre("Drama");

        when(bookRepository.findById(1)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(updatedBook)).thenReturn(updatedBook);

        String result = bookService.updateBook(updatedBook);

        assertEquals("El libro se ha actualizado correctamente.", result);
        verify(bookRepository, times(1)).findById(1);
        verify(bookRepository, times(1)).save(updatedBook);
    }

    @Test
    void testUpdateBookNotFound() {
        Book updatedBook = new Book();
        updatedBook.setId(2);
        updatedBook.setTitle("New Book");
        updatedBook.setPages(300);
        updatedBook.setPublishDate(LocalDate.now());
        updatedBook.setAuthor("New Author");
        updatedBook.setGenre("Fantasy");

        when(bookRepository.findById(2)).thenReturn(Optional.empty());

        String result = bookService.updateBook(updatedBook);

        assertEquals("Error: El libro no existe.", result);
        verify(bookRepository, times(1)).findById(2);
        verify(bookRepository, times(0)).save(updatedBook);
    }

    @Test
    void testFindAllBooksNotEmpty() {
        Book book = new Book();
        book.setId(1);
        book.setTitle("Test Book");
        book.setPages(200);
        book.setPublishDate(LocalDate.now());
        book.setAuthor("Test Author");
        book.setGenre("Fiction");
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        when(bookRepository.findAll()).thenReturn(bookList);

        List<Book> result = bookService.findAllBooks();

        assertEquals(bookList.size(), result.size());
        assertEquals(bookList.get(0), result.get(0));
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testFindByTitle() {
        String title = "Test Book";
        Book book = new Book();
        book.setTitle(title);

        when(bookRepository.findByTitle(title)).thenReturn(book);

        Book result = bookService.findByTitle(title);

        assertEquals(book, result);
        verify(bookRepository, times(1)).findByTitle(title);
    }

    @Test
    void testFindByPublishDate() {
        String publishDate = "2024-06-25";
        LocalDate date = LocalDate.parse(publishDate);
        
        Book book = new Book();
        book.setId(1);
        book.setTitle("Test Book");
        book.setPages(200);
        book.setPublishDate(date);
        book.setAuthor("Test Author");
        book.setGenre("Fiction");

        List<Book> bookList = Collections.singletonList(book);

        when(bookRepository.findByPublishDate(date)).thenReturn(bookList);

        List<Book> result = bookService.findByPublishDate(publishDate);

        assertEquals(bookList.size(), result.size());
        assertEquals(bookList.get(0), result.get(0));
        verify(bookRepository, times(1)).findByPublishDate(date);
    }

    @Test
    void testFindByAuthor() {
        String author = "Test Author";
        
        Book book = new Book();
        book.setId(1);
        book.setTitle("Test Book");
        book.setPages(200);
        book.setPublishDate(LocalDate.now());
        book.setAuthor(author);
        book.setGenre("Fiction");

        List<Book> bookList = Collections.singletonList(book);

        when(bookRepository.findByAuthor(author)).thenReturn(bookList);

        List<Book> result = bookService.findByAuthor(author);

        assertEquals(bookList.size(), result.size());
        assertEquals(bookList.get(0), result.get(0));
        verify(bookRepository, times(1)).findByAuthor(author);
    }

    @Test
    void testFindByGenre() {
        String genre = "Fiction";
        
        Book book = new Book();
        book.setId(1);
        book.setTitle("Test Book");
        book.setPages(200);
        book.setPublishDate(LocalDate.now());
        book.setAuthor("Test Author");
        book.setGenre(genre);

        List<Book> bookList = Collections.singletonList(book);

        when(bookRepository.findByGenre(genre)).thenReturn(bookList);

        List<Book> result = bookService.findByGenre(genre);

        assertEquals(bookList.size(), result.size());
        assertEquals(bookList.get(0), result.get(0));
        verify(bookRepository, times(1)).findByGenre(genre);
    }
}
