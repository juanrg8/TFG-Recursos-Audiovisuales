package com.juanromero.tfg.gestionrecursosaudiovisuales.mapper.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserBookRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.book.Book;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.BookStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserBook;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.book.BookRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;


@Service
public class UserBookMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public UserBook dtoToEntity(UserBookRequest dto) {
        UserBook entity = new UserBook();

        // Asignar el ID si está presente en el DTO
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }

        // Encontrar y asignar el usuario
        User usuario = userRepository.findById(dto.getUserId()).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        entity.setUsuario(usuario);
        
        // Encontrar y asignar el libro
        Book book = bookRepository.findById(dto.getBookId()).orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));
        entity.setBook(book);
        
        // Asignar el estado
        entity.setStatus(dto.getStatus());

        // Asignar la review y rating solo si el estado no es PENDING
        if (dto.getStatus() != BookStatus.PENDING) {
            entity.setReview(dto.getReview());
            entity.setRating(dto.getRating());
        }

        // Asignar las fechas
        entity.setDateStarted(dto.getDateStarted());
        entity.setDateRead(dto.getDateRead());
        
        return entity;
    }
    
    public UserBookRequest entityToDto(UserBook entity) {
    	UserBookRequest dto = new UserBookRequest();

        // Asignar el ID
        dto.setId(entity.getId());

        // Asignar el usuario
        dto.setUserId(entity.getUsuario().getId());
        
        // Asignar el libro
        dto.setBookId(entity.getBook().getId());
        
        // Asignar el estado
        dto.setStatus(entity.getStatus());

        // Asignar la review y rating
        dto.setReview(entity.getReview());
        dto.setRating(entity.getRating());

        // Asignar las fechas
        dto.setDateStarted(entity.getDateStarted());
        dto.setDateRead(entity.getDateRead());
        
        return dto;
    }
}
