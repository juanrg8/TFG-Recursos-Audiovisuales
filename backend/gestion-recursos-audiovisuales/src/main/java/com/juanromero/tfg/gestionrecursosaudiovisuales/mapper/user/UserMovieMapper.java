package com.juanromero.tfg.gestionrecursosaudiovisuales.mapper.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserMovieRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.movie.Movie;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserMovie;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.movie.MovieRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;

@Service
public class UserMovieMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    public UserMovie dtoToEntity(UserMovieRequest dto) {
        UserMovie entity = new UserMovie();

        // Asignar el ID si está presente en el DTO
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }

        // Encontrar y asignar el usuario
        User usuario = userRepository.findById(dto.getUserId()).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        entity.setUsuario(usuario);
        
        // Encontrar y asignar la película
        Movie movie = movieRepository.findById(dto.getMovieId()).orElse(null);
        if (movie == null) {
            throw new IllegalArgumentException("Película no encontrada");
        }
        entity.setMovie(movie);
        
        // Asignar el estado
        entity.setStatus(dto.getStatus());
        
        // Asignar la reseña
        entity.setReview(dto.getReview());
        
        // Asignar la calificación
        entity.setRating(dto.getRating());
        
        // Asignar la fecha de visualización
        entity.setDateWatched(dto.getDateWatched());

        return entity;
    }
    
    public UserMovieRequest entityToDTO(UserMovie entity) {
        UserMovieRequest dto = new UserMovieRequest();

        // Asignar el ID si está presente en el DTO
        if (entity.getId() != null) {
            dto.setId(entity.getId());
        }

        // Encontrar y asignar el usuario
        User usuario = userRepository.findById(entity.getUsuario().getId()).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        dto.setUserId(usuario.getId());
        
        // Encontrar y asignar la película
        Movie movie = movieRepository.findById(entity.getMovie().getId()).orElse(null);
        if (movie == null) {
            throw new IllegalArgumentException("Película no encontrada");
        }
        dto.setMovieId(movie.getId());
        
        // Asignar el estado
        dto.setStatus(entity.getStatus());
        
        // Asignar la reseña
        dto.setReview(entity.getReview());
        
        // Asignar la calificación
        dto.setRating(entity.getRating());
        
        // Asignar la fecha de visualización
        dto.setDateWatched(entity.getDateWatched());

        return dto;
    }
}
