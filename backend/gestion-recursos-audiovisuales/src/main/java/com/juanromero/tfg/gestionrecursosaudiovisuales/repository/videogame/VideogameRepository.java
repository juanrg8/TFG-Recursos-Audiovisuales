package com.juanromero.tfg.gestionrecursosaudiovisuales.repository.videogame;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.videogame.Videogame;

public interface VideogameRepository extends CrudRepository<Videogame, Integer> {
    
    @Query("SELECT videogame FROM Videogame videogame WHERE videogame.title LIKE :title")
    public Videogame findByTitle(@Param("title") String title);
    
    
}

