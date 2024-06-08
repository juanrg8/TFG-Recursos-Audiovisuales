package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserMovieRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserMovieResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.MovieStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserMovie;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user.UserMovieFacade;
import com.juanromero.tfg.gestionrecursosaudiovisuales.mapper.user.UserMovieMapper;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserMovieRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.UserMovieService;

@Service
public class UserMovieFacadeImpl implements UserMovieFacade {

    @Autowired
    private UserMovieService userMovieService;

    @Autowired
    private UserMovieMapper userMovieMapper;

    @Autowired
    private UserMovieRepository userMovieRepository;

    @Override
    public UserMovieResponse addUserMovie(UserMovieRequest userMovieRequest) {
        UserMovieResponse response = new UserMovieResponse();
        String descripcionPeticion = "";

        UserMovie userMovie = userMovieMapper.dtoToEntity(userMovieRequest);
        descripcionPeticion = userMovieService.addUserMovie(userMovie);
        response.setDescripcionPeticion(descripcionPeticion);
        userMovieRequest.setId(userMovie.getId());

        if (descripcionPeticion.contains("añadida")) {
            List<UserMovieRequest> userMovies = new ArrayList<>();
            response.setListaUserMovies(userMovies);
            response.getListaUserMovies().add(userMovieRequest);
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }

    @Override
    public UserMovieResponse deleteUserMovie(UserMovieRequest userMovieRequest) {
        UserMovieResponse response = new UserMovieResponse();
        String descripcionPeticion = "";

        descripcionPeticion = userMovieService.deleteUserMovie(userMovieRequest.getUserId(), userMovieRequest.getMovieId());
        response.setDescripcionPeticion(descripcionPeticion);

        if (descripcionPeticion.contains("eliminada")) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }

    @Override
    public UserMovieResponse findAllUserMovies(Integer usuarioId) {
        UserMovieResponse response = new UserMovieResponse();
        List<UserMovieRequest> userMoviesDTO = new ArrayList<>();
        List<UserMovie> userMovies = userMovieService.findAllUserMovies(usuarioId);
        for (UserMovie um : userMovies) {
            userMoviesDTO.add(userMovieMapper.entityToDTO(um));
        }
        response.setListaUserMovies(userMoviesDTO);

        if (!userMovies.isEmpty()) {
            response.setEstadoPeticion("OK");
            response.setDescripcionPeticion("Películas encontradas correctamente.");
        } else {
            response.setEstadoPeticion("KO");
            response.setDescripcionPeticion("No se encontraron películas para el usuario.");
        }

        return response;
    }

    @Override
    public UserMovieResponse findUserMoviesByStatus(Integer usuarioId, MovieStatus status) {
        UserMovieResponse response = new UserMovieResponse();

        List<UserMovie> userMovies = userMovieService.findUserMoviesByStatus(usuarioId, status);
        List<UserMovieRequest> userMoviesDTO = new ArrayList<>();
        for (UserMovie um : userMovies) {
            userMoviesDTO.add(userMovieMapper.entityToDTO(um));
        }
        response.setListaUserMovies(userMoviesDTO);

        if (!userMovies.isEmpty()) {
            response.setEstadoPeticion("OK");
            response.setDescripcionPeticion("Películas encontradas correctamente.");
        } else {
            response.setEstadoPeticion("KO");
            response.setDescripcionPeticion("No se encontraron películas para el usuario con el estado especificado.");
        }

        return response;
    }

    @Override
    public UserMovieResponse moveUserMovieToStatus(Integer usuarioId, Integer movieId, MovieStatus status) {
        UserMovieResponse response = new UserMovieResponse();
        Optional<UserMovie> userMovieOpt = userMovieRepository.findByUsuarioIdAndMovieId(usuarioId, movieId);
        
        if (userMovieOpt.isPresent()) {
            UserMovie userMovie = userMovieOpt.get();
            
            // Guardar la fecha de visualización si se cambia a WATCHED
            if (status == MovieStatus.WATCHED) {
                userMovie.setDateWatched(LocalDate.now());
            }
            
            userMovie.setStatus(status);
            userMovieRepository.save(userMovie);

            // Construir UserMovieRequest desde UserMovie modificado
            UserMovieRequest userMovieRequest = userMovieMapper.entityToDTO(userMovie);

            List<UserMovieRequest> userMovies = new ArrayList<>();
            userMovies.add(userMovieRequest);
            response.setListaUserMovies(userMovies);

            response.setEstadoPeticion("OK");
            response.setDescripcionPeticion("Película movida a la lista de " + userMovie.getStatus() + ".");
        } else {
            response.setDescripcionPeticion("La película no está en la lista.");
            response.setEstadoPeticion("KO");
        }

        return response;
    }


    @Override
    public UserMovieResponse updateUserMovieReview(Integer userId, Integer movieId, String review) {
        UserMovieResponse response = new UserMovieResponse();
        String descripcionPeticion = userMovieService.updateUserMovieReview(userId, movieId, review);
        response.setDescripcionPeticion(descripcionPeticion);

        if (descripcionPeticion.contains("actualizada")) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }

    @Override
    public UserMovieResponse updateUserMovieRating(Integer userId, Integer movieId, BigDecimal rating) {
        UserMovieResponse response = new UserMovieResponse();
        String descripcionPeticion = userMovieService.updateUserMovieRating(userId, movieId, rating);
        response.setDescripcionPeticion(descripcionPeticion);

        if (descripcionPeticion.contains("actualizada")) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }
}
