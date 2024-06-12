package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user;

import java.math.BigDecimal;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserAlbumRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserAlbumResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.AlbumStatus;

public interface UserAlbumFacade {
    
    UserAlbumResponse addUserAlbum(UserAlbumRequest userAlbumRequest);

    UserAlbumResponse deleteUserAlbum(UserAlbumRequest userAlbumRequest);

    UserAlbumResponse findAllUserAlbums(String usuarioNombre);

    UserAlbumResponse findUserAlbumsByStatus(String usuarioNombre, AlbumStatus status);

    UserAlbumResponse moveUserAlbumToStatus(String usuarioNombre, Integer albumId);
    
    UserAlbumResponse updateUserAlbumReview(String usuarioNombre, Integer albumId, String review);

    UserAlbumResponse updateUserAlbumRating(String usuarioNombre, Integer albumId, BigDecimal rating);
}
