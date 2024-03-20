package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.album;

import java.util.List;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album.Album;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumResponse {

    private String estadoPeticion;
    private String descripcionPeticion;
    private List<Album> listaAlbumes;

    public AlbumResponse(String estadoPeticion, String descripcionPeticion, List<Album> listaAlbumes) {
        this.estadoPeticion = estadoPeticion;
        this.descripcionPeticion = descripcionPeticion;
        this.listaAlbumes = listaAlbumes;
    }

    public AlbumResponse() {

    }
}

