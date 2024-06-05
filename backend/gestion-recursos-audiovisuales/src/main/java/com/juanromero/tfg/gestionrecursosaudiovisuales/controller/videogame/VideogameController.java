package com.juanromero.tfg.gestionrecursosaudiovisuales.controller.videogame;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.videogame.VideogameRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.videogame.VideogameResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.videogame.VideogameFacade;

@RestController
@RequestMapping(path="/videogame")
public class VideogameController {

    @Autowired
    private VideogameFacade videogameFacade;

    @PostMapping(path="/add")
    public VideogameResponse addVideogame(@RequestBody VideogameRequest request) {
        return videogameFacade.addVideogame(request);
    }

    @DeleteMapping(path="/delete")
    public VideogameResponse deleteVideogame(@RequestBody VideogameRequest request) {
        return videogameFacade.deleteVideogame(request);
    }

    @PutMapping(path="/update")
    public VideogameResponse updateVideogame(@RequestBody VideogameRequest request) {
        return videogameFacade.updateVideogame(request);
    }

    @GetMapping(path="/find")
    public VideogameResponse findVideogames() {
        return videogameFacade.findVideogames();
    }
    
    @GetMapping(path="/title")
    public List<VideogameRequest> findVideogamesByTitle(@RequestBody VideogameRequest request) {
        return videogameFacade.findByTitle(request.getTitle()).getListaVideogames();
    }
    
    @GetMapping(path="/platform")
    public List<VideogameRequest> findVideogamesByPlatform(@RequestBody VideogameRequest request) {
        return videogameFacade.findByPlatform(request.getPlatform()).getListaVideogames();
    }
    
    @GetMapping(path="/genre")
    public List<VideogameRequest> findVideogamesByGenre(@RequestBody VideogameRequest request) {
        return videogameFacade.findByGenre(request.getGenre()).getListaVideogames();
    }
    
    @GetMapping(path="/releaseDate")
    public List<VideogameRequest> findVideogamesByReleaseDate(@RequestBody VideogameRequest request) {
        return videogameFacade.findByReleaseDate(request.getReleaseDate().toString()).getListaVideogames();
    }
    
    @GetMapping(path="/developer")
    public List<VideogameRequest> findVideogamesByDeveloper(@RequestBody VideogameRequest request) {
        return videogameFacade.findByDeveloper(request.getDeveloper()).getListaVideogames();
    }
    
    @GetMapping(path="/publisher")
    public List<VideogameRequest> findVideogamesByPublisher(@RequestBody VideogameRequest request) {
        return videogameFacade.findByPublisher(request.getPublisher()).getListaVideogames();
    }
}
