package com.juanromero.tfg.gestionrecursosaudiovisuales.repository.book;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.book.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {
    
    @Query("SELECT book FROM Book book WHERE book.title LIKE :title")
    public Book findByTitle(@Param("title") String title);
}
