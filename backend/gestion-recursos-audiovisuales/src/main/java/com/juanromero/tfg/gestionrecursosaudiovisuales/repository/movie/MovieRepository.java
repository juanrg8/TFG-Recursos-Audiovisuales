package com.juanromero.tfg.gestionrecursosaudiovisuales.repository.movie;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.movie.Movie;

public interface MovieRepository extends CrudRepository<Movie, Integer> {

    @Query("SELECT m FROM Movie m WHERE m.title = :title")
    Movie findByTitle(@Param("title") String title);

    @Query("SELECT m FROM Movie m WHERE m.genre = :genre")
    List<Movie> findByGenre(@Param("genre") String genre);

    @Query("SELECT m FROM Movie m WHERE m.releaseDate = :releaseDate")
    List<Movie> findByReleaseDate(@Param("releaseDate") LocalDate releaseDate);

    @Query("SELECT m FROM Movie m WHERE m.director = :director")
    List<Movie> findByDirector(@Param("director") String director);

    @Query("SELECT m FROM Movie m WHERE m.studio = :studio")
    List<Movie> findByStudio(@Param("studio") String studio);
	
    
    
}
