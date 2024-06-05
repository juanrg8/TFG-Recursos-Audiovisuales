package com.juanromero.tfg.gestionrecursosaudiovisuales.repository.videogame;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.videogame.Videogame;

public interface VideogameRepository extends CrudRepository<Videogame, Integer> {
    
    @Query("SELECT videogame FROM Videogame videogame WHERE videogame.title LIKE :title")
    public Videogame findByTitle(@Param("title") String title);
    
    @Query("SELECT v FROM Videogame v WHERE v.platform = :platform")
    List<Videogame> findByPlatform(@Param("platform") String platform);

    @Query("SELECT v FROM Videogame v WHERE v.genre = :genre")
    List<Videogame> findByGenre(@Param("genre") String genre);

    @Query("SELECT v FROM Videogame v WHERE v.releaseDate = :releaseDate")
    List<Videogame> findByReleaseDate(@Param("releaseDate") LocalDate releaseDate);

    @Query("SELECT v FROM Videogame v WHERE v.developer = :developer")
    List<Videogame> findByDeveloper(@Param("developer") String developer);

    @Query("SELECT v FROM Videogame v WHERE v.publisher = :publisher")
    List<Videogame> findByPublisher(@Param("publisher") String publisher);
    
}

