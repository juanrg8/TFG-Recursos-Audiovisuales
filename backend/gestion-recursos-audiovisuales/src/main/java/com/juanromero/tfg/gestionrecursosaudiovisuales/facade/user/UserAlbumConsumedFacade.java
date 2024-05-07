package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserAlbumConsumedRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserAlbumConsumedResponse;

public interface UserAlbumConsumedFacade {
    
    UserAlbumConsumedResponse addAlbumConsumed(UserAlbumConsumedRequest request);

    UserAlbumConsumedResponse deleteAlbumConsumed(UserAlbumConsumedRequest request);

    UserAlbumConsumedResponse updateAlbumConsumed(UserAlbumConsumedRequest request);

    UserAlbumConsumedResponse findAllAlbumsConsumed();
}
