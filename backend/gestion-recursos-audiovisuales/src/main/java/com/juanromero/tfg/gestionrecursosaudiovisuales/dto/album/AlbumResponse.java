package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.album;

import java.util.List; 

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumResponse {

    private String estadoPeticion;
    private String descripcionPeticion;
    private List<AlbumRequest> listaAlbumes;

    public AlbumResponse(String estadoPeticion, String descripcionPeticion, List<AlbumRequest> listaAlbumes) {
        this.estadoPeticion = estadoPeticion;
        this.descripcionPeticion = descripcionPeticion;
        this.listaAlbumes = listaAlbumes;
    }

    public AlbumResponse() {

    }
}

