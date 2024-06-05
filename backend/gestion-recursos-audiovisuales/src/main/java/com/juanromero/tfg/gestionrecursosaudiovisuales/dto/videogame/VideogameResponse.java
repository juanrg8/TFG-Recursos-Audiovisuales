package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.videogame;



import java.util.List; 


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideogameResponse {

    private String estadoPeticion;
    private String descripcionPeticion;
    private List<VideogameRequest> listaVideogames;

    public VideogameResponse(String estadoPeticion, String descripcionPeticion, List<VideogameRequest> listaVideogames) {
        this.estadoPeticion = estadoPeticion;
        this.descripcionPeticion = descripcionPeticion;
        this.listaVideogames = listaVideogames;
    }

    public VideogameResponse() {

    }
}

