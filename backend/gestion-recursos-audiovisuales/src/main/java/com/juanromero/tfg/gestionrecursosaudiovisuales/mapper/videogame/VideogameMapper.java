package com.juanromero.tfg.gestionrecursosaudiovisuales.mapper.videogame;

import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.videogame.VideogameRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.videogame.Videogame;

@Service
public class VideogameMapper {
    
    public Videogame dtoToEntity(VideogameRequest dto) {
        Videogame entity = new Videogame();
        
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setPlatform(dto.getPlatform());
        entity.setGenre(dto.getGenre());
        entity.setReleaseYear(dto.getReleaseYear());
        entity.setDeveloper(dto.getDeveloper());
        entity.setPublisher(dto.getPublisher());
        
        return entity;
    }
}