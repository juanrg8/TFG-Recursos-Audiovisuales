package com.juanromero.tfg.gestionrecursosaudiovisuales.repository.book;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.book.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {
    
    @Query("SELECT book FROM Book book WHERE book.title LIKE :title")
    public Book findByTitle(@Param("title") String title);
    
    @Query("SELECT b FROM Book b WHERE b.publishDate = :publishDate")
    List<Book> findByPublishDate(@Param("publishDate") LocalDate publishDate);

    @Query("SELECT b FROM Book b WHERE b.author = :author")
    List<Book> findByAuthor(@Param("author") String author);

    @Query("SELECT b FROM Book b WHERE b.genre = :genre")
    List<Book> findByGenre(@Param("genre") String genre);
}
