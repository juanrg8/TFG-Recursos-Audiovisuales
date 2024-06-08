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

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserVideogameRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserVideogameResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.VideogameStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user.UserVideogameFacade;

@RestController
@RequestMapping(path = "/uservideogame")
public class UserVideogameController {

    @Autowired
    private UserVideogameFacade userVideogameFacade;

    @PostMapping(path = "/add")
    public UserVideogameResponse add(@RequestBody UserVideogameRequest request) {
        return userVideogameFacade.addUserVideogame(request);
    }

    @DeleteMapping(path = "/delete")
    public UserVideogameResponse delete(@RequestBody UserVideogameRequest request) {
        return userVideogameFacade.deleteUserVideogame(request);
    }

    @GetMapping(path = "/findall/{usuarioId}")
    public List<UserVideogameRequest> findAllUserVideogames(@PathVariable Integer usuarioId) {
        return userVideogameFacade.findAllUserVideogames(usuarioId).getListaUserVideogames();
    }

    @GetMapping(path = "/findbystatus/{usuarioId}/{status}")
    public List<UserVideogameRequest> findUserVideogamesByStatus(@PathVariable Integer usuarioId, @PathVariable VideogameStatus status) {
        return userVideogameFacade.findUserVideogamesByStatus(usuarioId, status).getListaUserVideogames();
    }

    @PutMapping(path = "/movetostatus")
    public UserVideogameResponse moveUserVideogameToStatus(@RequestBody UserVideogameRequest request) {
        return userVideogameFacade.moveUserVideogameToStatus(request.getUserId(), request.getVideogameId(), request.getStatus());
    }

    @PutMapping(path = "/updatereview")
    public UserVideogameResponse updateUserVideogameReview(@RequestBody UserVideogameRequest request) {
        return userVideogameFacade.updateUserVideogameReview(request.getUserId(), request.getVideogameId(), request.getReview());
    }

    @PutMapping(path = "/updaterating")
    public UserVideogameResponse updateUserVideogameRating(@RequestBody UserVideogameRequest request) {
        return userVideogameFacade.updateUserVideogameRating(request.getUserId(), request.getVideogameId(), request.getRating());
    }
}
