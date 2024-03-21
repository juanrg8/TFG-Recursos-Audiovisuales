package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.videogame;



import java.util.List;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.videogame.Videogame;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideogameResponse {

    private String estadoPeticion;
    private String descripcionPeticion;
    private List<Videogame> listaVideogames;

    public VideogameResponse(String estadoPeticion, String descripcionPeticion, List<Videogame> listaVideogames) {
        this.estadoPeticion = estadoPeticion;
        this.descripcionPeticion = descripcionPeticion;
        this.listaVideogames = listaVideogames;
    }

    public VideogameResponse() {

    }
}

