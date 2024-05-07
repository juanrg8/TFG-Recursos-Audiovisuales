package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user;

import java.util.List;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserAlbumPending;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAlbumPendingResponse {
    
    private String estadoPeticion;
    private String descripcionPeticion;
    private List<UserAlbumPending> listaAlbumesPendientes;
    
    public UserAlbumPendingResponse(String estadoPeticion, String descripcionPeticion, List<UserAlbumPending> listaAlbumesPendientes) {
        super();
        this.estadoPeticion = estadoPeticion;
        this.descripcionPeticion = descripcionPeticion;
        this.listaAlbumesPendientes = listaAlbumesPendientes;
    }

    public UserAlbumPendingResponse() {
        
    }
}
