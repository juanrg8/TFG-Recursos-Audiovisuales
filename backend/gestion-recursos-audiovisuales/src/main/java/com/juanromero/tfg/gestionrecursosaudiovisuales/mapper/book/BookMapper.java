package com.juanromero.tfg.gestionrecursosaudiovisuales.mapper.book;

import org.springframework.stereotype.Service; 

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.book.BookRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.book.Book;

@Service
public class BookMapper {
    
    public Book dtoToEntity(BookRequest dto) {
        Book entity = new Book();
        
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setPublishDate(dto.getPublishDate());
        entity.setAuthor(dto.getAuthor());
        entity.setGenre(dto.getGenre());
        
        return entity;
    }
    
    public BookRequest entityToDto(Book entity) {
    	BookRequest dto = new BookRequest();
        
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setPublishDate(entity.getPublishDate());
        dto.setAuthor(entity.getAuthor());
        dto.setGenre(entity.getGenre());
        
        return dto;
    }
    
}
