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
        entity.setReleaseDate(dto.getReleaseDate());
        entity.setDeveloper(dto.getDeveloper());
        entity.setPublisher(dto.getPublisher());
        
        return entity;
    }
    
    public VideogameRequest entityToDTO(Videogame entity) {
    	VideogameRequest dto = new VideogameRequest();
        
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setPlatform(entity.getPlatform());
        dto.setGenre(entity.getGenre());
        dto.setReleaseDate(entity.getReleaseDate());
        dto.setDeveloper(entity.getDeveloper());
        dto.setPublisher(entity.getPublisher());
        
        return dto;
    }
    
}