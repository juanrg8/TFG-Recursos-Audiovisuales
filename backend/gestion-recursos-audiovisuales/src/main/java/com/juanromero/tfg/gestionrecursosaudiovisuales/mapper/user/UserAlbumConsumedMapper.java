package com.juanromero.tfg.gestionrecursosaudiovisuales.mapper.user;

import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserAlbumConsumedRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album.Album;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserAlbumConsumed;

@Service
public class UserAlbumConsumedMapper {
    
    public UserAlbumConsumed dtoToEntity(UserAlbumConsumedRequest dto) {
        UserAlbumConsumed entity = new UserAlbumConsumed();
        
        entity.setId(dto.getId());
        entity.setUsuario(new User(dto.getUsuarioId())); 
        entity.setAlbum(new Album(dto.getAlbumId())); 
        
        return entity;
    }
}
