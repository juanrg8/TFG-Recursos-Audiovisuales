package com.juanromero.tfg.gestionrecursosaudiovisuales.controller.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.movie.MovieRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.movie.MovieResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.movie.MovieFacade;

import java.util.List;

@RestController
@RequestMapping(path = "/movie")
public class MovieController {

    @Autowired
    private MovieFacade movieFacade;

    @PostMapping(path = "/add")
    public MovieResponse addMovie(@RequestBody MovieRequest request) {
        return movieFacade.addMovie(request);
    }

    @DeleteMapping(path = "/delete")
    public MovieResponse deleteMovie(@RequestBody MovieRequest request) {
        return movieFacade.deleteMovie(request);
    }

    @PutMapping(path = "/update")
    public MovieResponse updateMovie(@RequestBody MovieRequest request) {
        return movieFacade.updateMovie(request);
    }

    @GetMapping(path = "/all")
    public List<MovieRequest> findAllMovies() {
        return movieFacade.findAllMovies().getListaPeliculas();
    }

    @GetMapping(path = "/title")
    public List<MovieRequest> findMoviesByTitle(@RequestBody MovieRequest request) {
        return movieFacade.findMoviesByTitle(request.getTitle()).getListaPeliculas();
    }

    @GetMapping(path = "/genre")
    public List<MovieRequest> findMoviesByGenre(@RequestBody MovieRequest request) {
        return movieFacade.findMoviesByGenre(request.getGenre()).getListaPeliculas();
    }

    @GetMapping(path = "/release")
    public List<MovieRequest> findMoviesByReleaseDate(@RequestBody MovieRequest request) {
        return movieFacade.findMoviesByReleaseDate(request.getReleaseDate().toString()).getListaPeliculas();
    }

    @GetMapping(path = "/director")
    public List<MovieRequest> findMoviesByDirector(@RequestBody MovieRequest request) {
        return movieFacade.findMoviesByDirector(request.getDirector()).getListaPeliculas();
    }

    @GetMapping(path = "/studio")
    public List<MovieRequest> findMoviesByStudio(@RequestBody MovieRequest request) {
        return movieFacade.findMoviesByStudio(request.getStudio()).getListaPeliculas();
    }
}
