package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAlbumPendingRequest {

    private Integer id;
    private Integer usuarioId;
    private Integer albumId;


    public UserAlbumPendingRequest(Integer id, Integer usuarioId, Integer albumId) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.albumId = albumId;
    }
}
