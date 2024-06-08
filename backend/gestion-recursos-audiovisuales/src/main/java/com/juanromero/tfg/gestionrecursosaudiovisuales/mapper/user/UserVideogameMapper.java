package com.juanromero.tfg.gestionrecursosaudiovisuales.mapper.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserVideogameRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserVideogame;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.videogame.Videogame;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.videogame.VideogameRepository;

@Service
public class UserVideogameMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideogameRepository videogameRepository;

    public UserVideogame dtoToEntity(UserVideogameRequest dto) {
        UserVideogame entity = new UserVideogame();

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
        
        // Encontrar y asignar el videojuego
        Videogame videogame = videogameRepository.findById(dto.getVideogameId()).orElse(null);
        if (videogame == null) {
            throw new IllegalArgumentException("Videojuego no encontrado");
        }
        entity.setVideogame(videogame);
        
        // Asignar el estado
        entity.setStatus(dto.getStatus());
        
        // Asignar la reseña
        entity.setReview(dto.getReview());
        
        // Asignar la calificación
        entity.setRating(dto.getRating());
        
        // Asignar la fecha de inicio
        entity.setDateStarted(dto.getDateStarted());
        
        // Asignar la fecha jugada
        entity.setDatePlayed(dto.getDatePlayed());

        return entity;
    }
    
    public UserVideogameRequest entityToDTO(UserVideogame entity) {
        UserVideogameRequest dto = new UserVideogameRequest();

        // Asignar el ID si está presente en el DTO
        if (entity.getId() != null) {
            dto.setId(entity.getId());
        }

        // Asignar el ID del usuario
        dto.setUserId(entity.getUsuario().getId());
        
        // Asignar el ID del videojuego
        dto.setVideogameId(entity.getVideogame().getId());
        
        // Asignar el estado
        dto.setStatus(entity.getStatus());
        
        // Asignar la reseña
        dto.setReview(entity.getReview());
        
        // Asignar la calificación
        dto.setRating(entity.getRating());
        
        // Asignar la fecha de inicio
        dto.setDateStarted(entity.getDateStarted());
        
        // Asignar la fecha jugada
        dto.setDatePlayed(entity.getDatePlayed());

        return dto;
    }
}
