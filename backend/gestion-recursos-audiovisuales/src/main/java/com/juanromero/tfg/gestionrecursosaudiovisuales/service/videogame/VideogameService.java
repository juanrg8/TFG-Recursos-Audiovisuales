package com.juanromero.tfg.gestionrecursosaudiovisuales.service.videogame;

import java.util.List;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.videogame.Videogame;

public interface VideogameService {
    
    String addVideogame(Videogame videogame);
    
    String deleteVideogame(Videogame videogame);
    
    String updateVideogame(Videogame videogame);
    
    List<Videogame> findAllVideogames();
    
    Videogame findByTitle(String title);

    List<Videogame> findByPlatform(String platform);

    List<Videogame> findByGenre(String genre);

    List<Videogame> findByReleaseDate(String releaseDate);

    List<Videogame> findByDeveloper(String developer);

    List<Videogame> findByPublisher(String publisher);
}
