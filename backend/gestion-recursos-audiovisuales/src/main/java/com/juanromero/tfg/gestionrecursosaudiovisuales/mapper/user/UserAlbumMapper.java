package com.juanromero.tfg.gestionrecursosaudiovisuales.mapper.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserAlbumRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album.Album;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserAlbum;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.album.AlbumRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;

@Service
public class UserAlbumMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlbumRepository albumRepository;

    public UserAlbum dtoToEntity(UserAlbumRequest dto) {
        UserAlbum entity = new UserAlbum();

        // Asignar el ID si está presente en el DTO
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }

        // Encontrar y asignar el usuario
        User usuario = userRepository.findById(dto.getUsuarioId()).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        entity.setUsuario(usuario);
        
        // Encontrar y asignar el álbum
        Album album = albumRepository.findById(dto.getAlbumId()).orElse(null);
        if (album == null) {
            throw new IllegalArgumentException("Álbum no encontrado");
        }
        entity.setAlbum(album);
        
        // Asignar el estado
        entity.setStatus(dto.getStatus());
        
        return entity;
    }
    
    public UserAlbumRequest entityToDTO(UserAlbum entity) {
    	UserAlbumRequest dto = new UserAlbumRequest();

        // Asignar el ID si está presente en el DTO
        if (entity.getId() != null) {
            dto.setId(entity.getId());
            System.out.println(dto.getId());
        }

        // Encontrar y asignar el usuario
        User usuario = userRepository.findById(entity.getUsuario().getId()).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        dto.setUsuarioId(usuario.getId());
        
        // Encontrar y asignar el álbum
        Album album = albumRepository.findById(entity.getAlbum().getId()).orElse(null);
        if (album == null) {
            throw new IllegalArgumentException("Álbum no encontrado");
        }
        dto.setAlbumId(album.getId());
        
        // Asignar el estado
        dto.setStatus(entity.getStatus());
        
        return dto;
    }
    
    
}
