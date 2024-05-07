package com.juanromero.tfg.gestionrecursosaudiovisuales.service.user;

import java.util.List;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserAlbumConsumed;

public interface UserAlbumConsumedService {
    
    public String addUserAlbumConsumed(UserAlbumConsumed userAlbumConsumed);
    
    public String deleteUserAlbumConsumed(UserAlbumConsumed userAlbumConsumed);

    public String updateUserAlbumConsumed(UserAlbumConsumed userAlbumConsumed);

    public List<UserAlbumConsumed> findAllUserAlbumsConsumed();
}
