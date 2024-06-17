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
        
        // Asignar el ID de Libro si está presente en el DTO
        if (dto.getLibroId() != null) {
            entity.setLibroId(dto.getLibroId());
        }

        // Encontrar y asignar el usuario
        User usuario = userRepository.findByUsername(dto.getUsuarioNombre()).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
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

        // Asignar el ID de Libro si está presente en el DTO
        if (entity.getLibroId() != null) {
            dto.setLibroId(entity.getLibroId());
        }
        
        // Encontrar y asignar el usuario
        User usuario = userRepository.findById(entity.getUsuario().getId()).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        dto.setUsuarioNombre(usuario.getUsername());
        
        // Encontrar y asignar el libro
        Book book = bookRepository.findById(entity.getBook().getId()).orElse(null);
        if (book == null) {
            throw new IllegalArgumentException("Libro no encontrado");
        }
        dto.setBookId(book.getId());
        
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
