package com.juanromero.tfg.gestionrecursosaudiovisuales.service.user;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserAlbumConsumedRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserAlbumPendingRequest;

public interface UserAlbumService {
    
    String addAlbumToPendingList(UserAlbumPendingRequest request);

    String addAlbumToConsumedList(UserAlbumConsumedRequest request);

    String deleteAlbumFromPendingList(UserAlbumPendingRequest request);

    String deleteAlbumFromConsumedList(UserAlbumConsumedRequest request);
}
