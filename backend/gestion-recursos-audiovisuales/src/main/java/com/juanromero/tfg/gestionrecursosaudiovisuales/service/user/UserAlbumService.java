package com.juanromero.tfg.gestionrecursosaudiovisuales.service.user;

import java.util.List;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserAlbum;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.AlbumStatus;

public interface UserAlbumService {
    
    String addUserAlbum(UserAlbum userAlbum);

    String deleteUserAlbum(Integer usuarioId, Integer albumId);

    List<UserAlbum> findAllUserAlbums(Integer usuarioId);

    List<UserAlbum> findUserAlbumsByStatus(Integer usuarioId, AlbumStatus status);

    String moveUserAlbumToStatus(Integer usuarioId, Integer albumId);

	UserAlbum findUserAlbumById(Integer id);
}
