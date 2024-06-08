package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user;

import java.math.BigDecimal;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserMovieRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserMovieResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.MovieStatus;

public interface UserMovieFacade {
    
    UserMovieResponse addUserMovie(UserMovieRequest userMovieRequest);

    UserMovieResponse deleteUserMovie(UserMovieRequest userMovieRequest);

    UserMovieResponse findAllUserMovies(Integer usuarioId);

    UserMovieResponse findUserMoviesByStatus(Integer usuarioId, MovieStatus status);

    UserMovieResponse moveUserMovieToStatus(Integer usuarioId, Integer movieId, MovieStatus status);
    
    UserMovieResponse updateUserMovieReview(Integer userId, Integer movieId, String review);

    UserMovieResponse updateUserMovieRating(Integer userId, Integer movieId, BigDecimal rating);
}
