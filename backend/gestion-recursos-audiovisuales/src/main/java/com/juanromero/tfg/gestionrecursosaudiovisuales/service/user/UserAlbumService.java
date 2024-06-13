package com.juanromero.tfg.gestionrecursosaudiovisuales.service.user;

import java.math.BigDecimal;
import java.util.List;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.AlbumStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserAlbum;

public interface UserAlbumService {
    
    String addUserAlbum(UserAlbum userAlbum);

    String deleteUserAlbum(Integer usuarioId, Integer albumId);

    List<UserAlbum> findAllUserAlbums(Integer usuarioId);

    List<UserAlbum> findUserAlbumsByStatus(Integer usuarioId, AlbumStatus status);

    String moveUserAlbumToStatus(Integer usuarioId, Integer albumId, AlbumStatus status);
    
    String updateUserAlbumReview(Integer userId, Integer albumId, String review);

    String updateUserAlbumRating(Integer userId, Integer albumId, BigDecimal rating);

	UserAlbum findUserAlbumById(Integer id);
}
