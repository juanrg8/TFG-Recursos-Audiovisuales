package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserAlbumPendingRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserAlbumPendingResponse;

public interface UserAlbumPendingFacade {
    
    UserAlbumPendingResponse addAlbumPending(UserAlbumPendingRequest request);

    UserAlbumPendingResponse deleteAlbumPending(UserAlbumPendingRequest request);

    UserAlbumPendingResponse updateAlbumPending(UserAlbumPendingRequest request);

    UserAlbumPendingResponse findAllAlbumsPending();
}
