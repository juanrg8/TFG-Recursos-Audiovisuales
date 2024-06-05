package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserAlbumRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserAlbumResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.AlbumStatus;

public interface UserAlbumFacade {
    
    UserAlbumResponse addUserAlbum(UserAlbumRequest userAlbumRequest);

    UserAlbumResponse deleteUserAlbum(UserAlbumRequest userAlbumRequest);

    UserAlbumResponse findAllUserAlbums(Integer usuarioId);

    UserAlbumResponse findUserAlbumsByStatus(Integer usuarioId, AlbumStatus status);

    UserAlbumResponse moveUserAlbumToStatus(Integer usuarioId, Integer albumId);
}
