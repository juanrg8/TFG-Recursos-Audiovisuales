package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.movie.impl;

import java.util.ArrayList; 
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.movie.MovieRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.movie.MovieResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.movie.Movie;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.movie.MovieFacade;
import com.juanromero.tfg.gestionrecursosaudiovisuales.mapper.movie.MovieMapper;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.movie.MovieService;

@Service
public class MovieFacadeImpl implements MovieFacade {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieMapper movieMapper;

    private static final String response_guardado_ok = "Película guardada con éxito";
    private static final String response_borrado_ok = "La película se ha borrado correctamente.";
    private static final String response_actualizado_ok = "La película se ha actualizado correctamente.";
    private static final String response_encontrado_ok = "Películas encontradas correctamente.";

    @Override
    public MovieResponse addMovie(MovieRequest movieRequest) {
        MovieResponse response = new MovieResponse();
        String descripcionPeticion = "";

        Movie movie = movieMapper.dtoToEntity(movieRequest);
        descripcionPeticion = movieService.addMovie(movie);
        response.setDescripcionPeticion(descripcionPeticion);
        movieRequest.setId(movie.getId());

        if (descripcionPeticion.equalsIgnoreCase(response_guardado_ok)) {
        	
            List<MovieRequest> movies = new ArrayList<>();
            response.setListaPeliculas(movies);
            response.getListaPeliculas().add(movieRequest);
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }

    @Override
    public MovieResponse deleteMovie(MovieRequest movieRequest) {
        MovieResponse response = new MovieResponse();
        String descripcionPeticion = "";

        Movie movie = movieMapper.dtoToEntity(movieRequest);
        descripcionPeticion = movieService.deleteMovie(movie);
        response.setDescripcionPeticion(descripcionPeticion);

        if (descripcionPeticion.equalsIgnoreCase(response_borrado_ok)) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }

    @Override
    public MovieResponse updateMovie(MovieRequest movieRequest) {
        MovieResponse response = new MovieResponse();
        String descripcionPeticion = "";

        Movie movie = movieMapper.dtoToEntity(movieRequest);
        descripcionPeticion = movieService.updateMovie(movie);
        response.setDescripcionPeticion(descripcionPeticion);

        if (descripcionPeticion.equalsIgnoreCase(response_actualizado_ok)) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }

    @Override
    public MovieResponse findAllMovies() {
        MovieResponse response = new MovieResponse();
        String descripcionPeticion = "No se han encontrado películas";

        List<Movie> lista = movieService.findAllMovies();
        List<MovieRequest> listaDTO = lista.stream()
            .map(movieMapper::entityToDTO)
            .collect(Collectors.toList());
        response.setListaPeliculas(listaDTO);

        if (lista.isEmpty()) {
            response.setEstadoPeticion("KO");
            response.setDescripcionPeticion(descripcionPeticion);
        } else {
            response.setEstadoPeticion("OK");
            response.setDescripcionPeticion(response_encontrado_ok);
        }

        return response;
    }

    @Override
    public MovieResponse findMoviesByTitle(String title) {
        MovieResponse response = new MovieResponse();
        String descripcionPeticion = "No se han encontrado películas";

        Movie pelicula = movieService.findMoviesByTitle(title);

        if (pelicula == null) {
            response.setEstadoPeticion("KO");
            response.setDescripcionPeticion(descripcionPeticion);
        } else {
            List<MovieRequest> listaDTO = List.of(movieMapper.entityToDTO(pelicula));
            response.setListaPeliculas(listaDTO);
            response.setEstadoPeticion("OK");
            response.setDescripcionPeticion("Película encontrada correctamente");
        }

        return response;
    }


    @Override
    public MovieResponse findMoviesByGenre(String genre) {
        MovieResponse response = new MovieResponse();
        String descripcionPeticion = "No se han encontrado películas";

        List<Movie> lista = movieService.findMoviesByGenre(genre);
        List<MovieRequest> listaDTO = lista.stream()
            .map(movieMapper::entityToDTO)
            .collect(Collectors.toList());
        response.setListaPeliculas(listaDTO);

        if (lista.isEmpty()) {
            response.setEstadoPeticion("KO");
            response.setDescripcionPeticion(descripcionPeticion);
        } else {
            response.setEstadoPeticion("OK");
            response.setDescripcionPeticion(response_encontrado_ok);
        }

        return response;
    }

    @Override
    public MovieResponse findMoviesByReleaseDate(String releaseDate) {
        MovieResponse response = new MovieResponse();
        String descripcionPeticion = "No se han encontrado películas";

        List<Movie> lista = movieService.findMoviesByReleaseDate(releaseDate);
        List<MovieRequest> listaDTO = lista.stream()
            .map(movieMapper::entityToDTO)
            .collect(Collectors.toList());
        response.setListaPeliculas(listaDTO);

        if (lista.isEmpty()) {
            response.setEstadoPeticion("KO");
            response.setDescripcionPeticion(descripcionPeticion);
        } else {
            response.setEstadoPeticion("OK");
            response.setDescripcionPeticion(response_encontrado_ok);
        }

        return response;
    }

    @Override
    public MovieResponse findMoviesByDirector(String director) {
        MovieResponse response = new MovieResponse();
        String descripcionPeticion = "No se han encontrado películas";

        List<Movie> lista = movieService.findMoviesByDirector(director);
        List<MovieRequest> listaDTO = lista.stream()
            .map(movieMapper::entityToDTO)
            .collect(Collectors.toList());
        response.setListaPeliculas(listaDTO);

        if (lista.isEmpty()) {
            response.setEstadoPeticion("KO");
            response.setDescripcionPeticion(descripcionPeticion);
        } else {
            response.setEstadoPeticion("OK");
            response.setDescripcionPeticion(response_encontrado_ok);
        }

        return response;
    }

    @Override
    public MovieResponse findMoviesByStudio(String studio) {
        MovieResponse response = new MovieResponse();
        String descripcionPeticion = "No se han encontrado películas";

        List<Movie> lista = movieService.findMoviesByStudio(studio);
        List<MovieRequest> listaDTO = lista.stream()
            .map(movieMapper::entityToDTO)
            .collect(Collectors.toList());
        response.setListaPeliculas(listaDTO);

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
