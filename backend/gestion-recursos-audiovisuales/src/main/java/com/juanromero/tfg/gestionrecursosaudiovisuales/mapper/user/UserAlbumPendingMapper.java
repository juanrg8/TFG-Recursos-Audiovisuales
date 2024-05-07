package com.juanromero.tfg.gestionrecursosaudiovisuales.mapper.user;

import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserAlbumPendingRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album.Album;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserAlbumPending;

@Service
public class UserAlbumPendingMapper {
    
    public UserAlbumPending dtoToEntity(UserAlbumPendingRequest dto) {
        UserAlbumPending entity = new UserAlbumPending();
        
        // Mapear los campos de la solicitud al objeto de la entidad
        entity.setId(dto.getId());
        // Asignar el usuario y el álbum a partir de las IDs proporcionadas en la solicitud
        entity.setUsuario(new User(dto.getUsuarioId())); // Suponiendo que User tiene un constructor que toma el ID como parámetro
        entity.setAlbum(new Album(dto.getAlbumId())); // Suponiendo que Album tiene un constructor que toma el ID como parámetro
        
        return entity;
    }
}
