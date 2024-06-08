package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user;

import java.math.BigDecimal;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserVideogameRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserVideogameResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.VideogameStatus;

public interface UserVideogameFacade {
    
    UserVideogameResponse addUserVideogame(UserVideogameRequest userVideogameRequest);

    UserVideogameResponse deleteUserVideogame(UserVideogameRequest userVideogameRequest);

    UserVideogameResponse findAllUserVideogames(Integer usuarioId);

    UserVideogameResponse findUserVideogamesByStatus(Integer usuarioId, VideogameStatus status);

    UserVideogameResponse moveUserVideogameToStatus(Integer usuarioId, Integer videogameId, VideogameStatus status);
    
    UserVideogameResponse updateUserVideogameReview(Integer userId, Integer videogameId, String review);

    UserVideogameResponse updateUserVideogameRating(Integer userId, Integer videogameId, BigDecimal rating);
}
