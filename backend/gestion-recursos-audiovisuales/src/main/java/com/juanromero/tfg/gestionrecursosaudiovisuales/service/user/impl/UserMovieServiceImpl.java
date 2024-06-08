package com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.MovieStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserMovie;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserMovieRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.UserMovieService;

@Service
public class UserMovieServiceImpl implements UserMovieService {

    @Autowired
    private UserMovieRepository userMovieRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public String addUserMovie(UserMovie userMovie) {
        // Verificar si la película ya está en la lista del usuario
        Optional<UserMovie> existingMovie = userMovieRepository.findByUsuarioIdAndMovieId(userMovie.getUsuario().getId(), userMovie.getMovie().getId());
        if (existingMovie.isPresent()) {
            return "La película ya está en la lista del usuario.";
        }

        // Guardar el UserMovie
        userMovieRepository.save(userMovie);

        // Añadir el UserMovie a la lista del usuario
        userMovie.getUsuario().getUserMovies().add(userMovie);
        userRepository.save(userMovie.getUsuario());

        return "Película añadida a la lista del usuario.";
    }

    @Transactional
    @Override
    public String deleteUserMovie(Integer userId, Integer movieId) {
        // Verificar si la película está en la lista del usuario
        Optional<UserMovie> existingMovie = userMovieRepository.findByUsuarioIdAndMovieId(userId, movieId);
        if (!existingMovie.isPresent()) {
            return "La película no está en la lista del usuario.";
        }

        // Eliminar el UserMovie
        UserMovie userMovie = existingMovie.get();
        userMovieRepository.delete(userMovie);

        // Eliminar el UserMovie de la lista del usuario
        userMovie.getUsuario().getUserMovies().remove(userMovie);
        userRepository.save(userMovie.getUsuario());

        return "Película eliminada de la lista del usuario.";
    }

    @Override
    public List<UserMovie> findAllUserMovies(Integer userId) {
        return userMovieRepository.findByUsuarioId(userId);
    }

    @Override
    public List<UserMovie> findUserMoviesByStatus(Integer userId, MovieStatus status) {
        return userMovieRepository.findByUsuarioIdAndStatus(userId, status);
    }

    @Transactional
    @Override
    public String moveUserMovieToStatus(Integer userId, Integer movieId, MovieStatus status) {
        Optional<UserMovie> userMovieOpt = userMovieRepository.findByUsuarioIdAndMovieId(userId, movieId);
        if (userMovieOpt.isPresent()) {
            UserMovie userMovie = userMovieOpt.get();
            userMovie.setStatus(status);

            // Guardar la fecha de visualización si se cambia a WATCHED
            if (status == MovieStatus.WATCHED) {
                userMovie.setDateWatched(LocalDate.now());
            }

            userMovieRepository.save(userMovie);
            return "Película movida a la lista de " + status + ".";
        }
        return "La película no está en la lista.";
    }

    @Override
    public String updateUserMovieReview(Integer userId, Integer movieId, String review) {
        Optional<UserMovie> userMovieOpt = userMovieRepository.findByUsuarioIdAndMovieId(userId, movieId);
        if (userMovieOpt.isPresent()) {
            UserMovie userMovie = userMovieOpt.get();
            userMovie.setReview(review);
            userMovieRepository.save(userMovie);
            return "Reseña de la película actualizada.";
        }
        return "La película no está en la lista.";
    }

    @Override
    public String updateUserMovieRating(Integer userId, Integer movieId, BigDecimal rating) {
        Optional<UserMovie> userMovieOpt = userMovieRepository.findByUsuarioIdAndMovieId(userId, movieId);
        if (userMovieOpt.isPresent()) {
            UserMovie userMovie = userMovieOpt.get();
            userMovie.setRating(rating);
            userMovieRepository.save(userMovie);
            return "Calificación de la película actualizada.";
        }
        return "La película no está en la lista.";
    }

    @Override
    public UserMovie findUserMovieById(Integer id) {
        return userMovieRepository.findById(id).orElse(null);
    }
}
