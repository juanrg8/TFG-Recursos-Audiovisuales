package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user;

import java.util.List;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserAlbumConsumed;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAlbumConsumedResponse {
    
    private String estadoPeticion;
    private String descripcionPeticion;
    private List<UserAlbumConsumed> listaAlbumesConsumidos;
    
    public UserAlbumConsumedResponse(String estadoPeticion, String descripcionPeticion, List<UserAlbumConsumed> listaAlbumesConsumidos) {
        super();
        this.estadoPeticion = estadoPeticion;
        this.descripcionPeticion = descripcionPeticion;
        this.listaAlbumesConsumidos = listaAlbumesConsumidos;
    }

    public UserAlbumConsumedResponse() {
        
    }
}
