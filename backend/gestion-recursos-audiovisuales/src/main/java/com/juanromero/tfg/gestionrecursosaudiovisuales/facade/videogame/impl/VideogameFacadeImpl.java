package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.videogame.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.videogame.VideogameRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.videogame.VideogameResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.videogame.Videogame;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.videogame.VideogameFacade;
import com.juanromero.tfg.gestionrecursosaudiovisuales.mapper.videogame.VideogameMapper;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.videogame.VideogameService;

@Service
public class VideogameFacadeImpl implements VideogameFacade {

    @Autowired
    private VideogameService videogameService;

    @Autowired
    private VideogameMapper videogameMapper;

    private static final String response_guardado_ok = "Videojuego guardado con éxito";
    private static final String response_borrado_ok = "El videojuego se ha borrado correctamente.";
    private static final String response_actualizado_ok = "El videojuego se ha actualizado correctamente.";
    private static final String response_encontrado_ok = "Videojuegos encontrados correctamente.";

    @Override
    public VideogameResponse addVideogame(VideogameRequest videogameRequest) {
        VideogameResponse response = new VideogameResponse();
        String descripcionPeticion = "";

        Videogame videogame = videogameMapper.dtoToEntity(videogameRequest);
        descripcionPeticion = videogameService.addVideogame(videogame);
        response.setDescripcionPeticion(descripcionPeticion);

        if (descripcionPeticion.equalsIgnoreCase(response_guardado_ok)) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }

    @Override
    public VideogameResponse deleteVideogame(VideogameRequest videogameRequest) {
        VideogameResponse response = new VideogameResponse();
        String descripcionPeticion = "";

        Videogame videogame = videogameMapper.dtoToEntity(videogameRequest);
        descripcionPeticion = videogameService.deleteVideogame(videogame);
        response.setDescripcionPeticion(descripcionPeticion);

        if (descripcionPeticion.equalsIgnoreCase(response_borrado_ok)) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }

    @Override
    public VideogameResponse updateVideogame(VideogameRequest videogameRequest) {
        VideogameResponse response = new VideogameResponse();
        String descripcionPeticion = "";

        Videogame videogame = videogameMapper.dtoToEntity(videogameRequest);
        descripcionPeticion = videogameService.updateVideogame(videogame);
        response.setDescripcionPeticion(descripcionPeticion);

        if (descripcionPeticion.equalsIgnoreCase(response_actualizado_ok)) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }

    @Override
    public VideogameResponse findVideogames() {
        VideogameResponse response = new VideogameResponse();
        String descripcionPeticion = "No se han encontrado videojuegos";

        List<Videogame> lista = videogameService.findAllVideogames();
        response.setListaVideogames(lista);
        if (lista.isEmpty()) {
            response.setEstadoPeticion("KO");
            response.setDescripcionPeticion(descripcionPeticion);
        } else {
            response.setEstadoPeticion("OK");
            response.setDescripcionPeticion(response_encontrado_ok);
        }

        return response;
    }
}

