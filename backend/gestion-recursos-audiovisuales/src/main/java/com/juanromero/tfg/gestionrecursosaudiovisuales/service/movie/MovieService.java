package com.juanromero.tfg.gestionrecursosaudiovisuales.service.movie;

import java.util.List;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.movie.Movie;


public interface MovieService {

    String addMovie(Movie movie);
    
    String deleteMovie(Movie movie);
    
    String updateMovie(Movie movie);
    
    List<Movie> findAllMovies();
    
    Movie findMoviesByTitle(String title);
    
    List<Movie> findMoviesByGenre(String genre);
    
    List<Movie> findMoviesByReleaseDate(String releaseDate);
    
    List<Movie> findMoviesByDirector(String director);
    
    List<Movie> findMoviesByStudio(String studio);
    
}
