package com.juanromero.tfg.gestionrecursosaudiovisuales.mapper.movie;

import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.movie.MovieRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.movie.Movie;

@Service
public class MovieMapper {

    public Movie dtoToEntity(MovieRequest dto) {
        Movie entity = new Movie();

        // Asignar el ID si está presente en el DTO
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }

        // Asignar los demás atributos
        entity.setTitle(dto.getTitle());
        entity.setGenre(dto.getGenre());
        entity.setReleaseDate(dto.getReleaseDate());
        entity.setDirector(dto.getDirector());
        entity.setStudio(dto.getStudio());

        return entity;
    }
    
    public MovieRequest entityToDTO(Movie entity) {
        MovieRequest dto = new MovieRequest();

        // Asignar el ID si está presente en la entidad
        if (entity.getId() != null) {
            dto.setId(entity.getId());
        }

        // Asignar los demás atributos
        dto.setTitle(entity.getTitle());
        dto.setGenre(entity.getGenre());
        dto.setReleaseDate(entity.getReleaseDate());
        dto.setDirector(entity.getDirector());
        dto.setStudio(entity.getStudio());

        return dto;
    }
}
