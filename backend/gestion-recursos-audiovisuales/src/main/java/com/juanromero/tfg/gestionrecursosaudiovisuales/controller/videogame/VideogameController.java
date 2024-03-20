package com.juanromero.tfg.gestionrecursosaudiovisuales.controller.videogame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.videogame.VideogameRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.videogame.VideogameResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.videogame.VideogameFacade;

@RestController
@RequestMapping("/videogame")
public class VideogameController {

    @Autowired
    private VideogameFacade videogameFacade;

    @PostMapping("/addvideogame")
    public VideogameResponse addVideogame(@RequestBody VideogameRequest request) {
        return videogameFacade.addVideogame(request);
    }

    @DeleteMapping("/deletevideogame")
    public VideogameResponse deleteVideogame(@RequestBody VideogameRequest request) {
        return videogameFacade.deleteVideogame(request);
    }

    @PutMapping("/updatevideogame")
    public VideogameResponse updateVideogame(@RequestBody VideogameRequest request) {
        return videogameFacade.updateVideogame(request);
    }

    @GetMapping("/findvideogames")
    public VideogameResponse findAllVideogames() {
        return videogameFacade.findVideogames();
    }
}

