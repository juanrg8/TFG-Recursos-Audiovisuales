package com.juanromero.tfg.gestionrecursosaudiovisuales.service.movie.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.movie.Movie;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.movie.MovieRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.movie.MovieService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public String addMovie(Movie movie) {
        Movie existingMovie = movieRepository.findByTitle(movie.getTitle());

        if (existingMovie == null) {
            movieRepository.save(movie);
            return "Película guardada con éxito";
        } else {
            return "No ha sido posible guardar la película, título duplicado";
        }
    }

    @Override
    @Transactional
    public String deleteMovie(Movie movie) {
        Optional<Movie> existingMovie = movieRepository.findById(movie.getId());
        if (existingMovie.isPresent()) {
            movieRepository.delete(movie);
            // Intentar cargar la película borrada nuevamente
            Movie deletedMovie = movieRepository.findByTitle(movie.getTitle());
            if (deletedMovie == null) {
                return "La película se ha borrado correctamente.";
            } else {
                return "Error: La película no se ha podido borrar.";
            }
        } else {
            return "Error: La película no existe.";
        }
    }

    @Override
    @Transactional
    public String updateMovie(Movie movie) {
        Optional<Movie> existingMovie = movieRepository.findById(movie.getId());
        if (existingMovie.isPresent()) {
            movieRepository.save(movie);
            return "La película se ha actualizado correctamente.";
        } else {
            return "Error: La película no existe.";
        }
    }

    @Override
    public List<Movie> findAllMovies() {
        Iterable<Movie> allMovies = movieRepository.findAll();
        if (allMovies != null) {
            List<Movie> lista = new ArrayList<>();
            for (Movie elemento : allMovies) {
                lista.add(elemento);
            }
            if (!lista.isEmpty()) {
                return lista;
            }
        }
        return null;
    }

    @Override
    public Movie findMoviesByTitle(String title) {
        Movie movie = movieRepository.findByTitle(title);
        return movie;
    }

    @Override
    public List<Movie> findMoviesByGenre(String genre) {
        List<Movie> movies = movieRepository.findByGenre(genre);
        return movies.isEmpty() ? null : movies;
    }

    @Override
    public List<Movie> findMoviesByReleaseDate(String releaseDate) {
        LocalDate date = LocalDate.parse(releaseDate);
        List<Movie> movies = movieRepository.findByReleaseDate(date);
        return movies.isEmpty() ? null : movies;
    }

    @Override
    public List<Movie> findMoviesByDirector(String director) {
        List<Movie> movies = movieRepository.findByDirector(director);
        return movies.isEmpty() ? null : movies;
    }

    @Override
    public List<Movie> findMoviesByStudio(String studio) {
        List<Movie> movies = movieRepository.findByStudio(studio);
        return movies.isEmpty() ? null : movies;
    }
}
