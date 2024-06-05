package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.movie;


import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.movie.MovieRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.movie.MovieResponse;

public interface MovieFacade {
	
    MovieResponse addMovie(MovieRequest movieRequest);
    
    MovieResponse deleteMovie(MovieRequest movieRequest);
    
    MovieResponse updateMovie(MovieRequest movieRequest);
    
    MovieResponse findAllMovies();
    
    MovieResponse findMoviesByTitle(String title);
    
    MovieResponse findMoviesByGenre(String genre);
    
    MovieResponse findMoviesByReleaseDate(String releaseDate);
    
    MovieResponse findMoviesByDirector(String director);
    
    MovieResponse findMoviesByStudio(String studio);

}
