package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.videogame.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.videogame.VideogameRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.videogame.VideogameResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.videogame.VideogameFacade;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.videogame.VideogameService;

@Service
public class VideogameFacadeImpl implements VideogameFacade {

    @Autowired
    private VideogameService videogameService;

    @Override
    public VideogameResponse addVideogame(VideogameRequest request) {
        return null;// Lógica para agregar un videojuego
    }

    public VideogameResponse deleteVideogame(VideogameRequest request) {
    	return null;// Lógica para eliminar un videojuego
    }

    public VideogameResponse updateVideogame(VideogameRequest request) {
    	return null;// Lógica para actualizar un videojuego
    }

    public VideogameResponse findVideogames() {
    	return null;// Lógica para encontrar todos los videojuegos
    }
}

