package com.juanromero.tfg.gestionrecursosaudiovisuales.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserMovieRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserMovieResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.MovieStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user.UserMovieFacade;

@RestController
@RequestMapping(path = "/usermovie")
public class UserMovieController {

    @Autowired
    private UserMovieFacade userMovieFacade;

    @PostMapping(path = "/add")
    public UserMovieResponse add(@RequestBody UserMovieRequest request) {
        return userMovieFacade.addUserMovie(request);
    }

    @DeleteMapping(path = "/delete")
    public UserMovieResponse delete(@RequestBody UserMovieRequest request) {
        return userMovieFacade.deleteUserMovie(request);
    }

    @GetMapping(path = "/findall/{usuarioId}")
    public List<UserMovieRequest> findAllUserMovies(@PathVariable Integer usuarioId) {
        return userMovieFacade.findAllUserMovies(usuarioId).getListaUserMovies();
    }

    @GetMapping(path = "/findbystatus/{usuarioId}/{status}")
    public List<UserMovieRequest> findUserMoviesByStatus(@PathVariable Integer usuarioId, @PathVariable MovieStatus status) {
        return userMovieFacade.findUserMoviesByStatus(usuarioId, status).getListaUserMovies();
    }

    @PutMapping(path = "/movetostatus")
    public UserMovieResponse moveUserMovieToStatus(@RequestBody UserMovieRequest request) {
        return userMovieFacade.moveUserMovieToStatus(request.getUserId(), request.getMovieId(), request.getStatus());
    }

    @PutMapping(path = "/updatereview")
    public UserMovieResponse updateUserMovieReview(@RequestBody UserMovieRequest request) {
        return userMovieFacade.updateUserMovieReview(request.getUserId(), request.getMovieId(), request.getReview());
    }

    @PutMapping(path = "/updaterating")
    public UserMovieResponse updateUserMovieRating(@RequestBody UserMovieRequest request) {
        return userMovieFacade.updateUserMovieRating(request.getUserId(), request.getMovieId(), request.getRating());
    }
}
