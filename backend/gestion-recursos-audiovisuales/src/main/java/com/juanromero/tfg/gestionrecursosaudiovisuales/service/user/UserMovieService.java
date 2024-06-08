package com.juanromero.tfg.gestionrecursosaudiovisuales.service.user;

import java.math.BigDecimal;
import java.util.List;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.MovieStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserMovie;

public interface UserMovieService {
    
    String addUserMovie(UserMovie userMovie);

    String deleteUserMovie(Integer userId, Integer movieId);

    List<UserMovie> findAllUserMovies(Integer userId);

    List<UserMovie> findUserMoviesByStatus(Integer userId, MovieStatus status);

    String moveUserMovieToStatus(Integer userId, Integer movieId, MovieStatus status);

    String updateUserMovieReview(Integer userId, Integer movieId, String review);

    String updateUserMovieRating(Integer userId, Integer movieId, BigDecimal rating);

    UserMovie findUserMovieById(Integer id);
}
