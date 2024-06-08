package com.juanromero.tfg.gestionrecursosaudiovisuales.service.user;

import java.math.BigDecimal;
import java.util.List;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserVideogame;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.VideogameStatus;

public interface UserVideogameService {
    
    String addUserVideogame(UserVideogame userVideogame);

    String deleteUserVideogame(Integer userId, Integer videogameId);

    List<UserVideogame> findAllUserVideogames(Integer userId);

    List<UserVideogame> findUserVideogamesByStatus(Integer userId, VideogameStatus status);

    public String moveUserVideogameToStatus(Integer usuarioId, Integer videogameId, VideogameStatus status);

    String updateUserVideogameReview(Integer userId, Integer videogameId, String review);

    String updateUserVideogameRating(Integer userId, Integer videogameId, BigDecimal rating);

    UserVideogame findUserVideogameById(Integer id);
}
