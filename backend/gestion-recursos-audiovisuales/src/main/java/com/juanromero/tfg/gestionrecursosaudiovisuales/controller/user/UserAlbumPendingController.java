package com.juanromero.tfg.gestionrecursosaudiovisuales.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserAlbumPendingRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserAlbumPendingResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user.UserAlbumPendingFacade;

@RestController
@RequestMapping(path="/user/albumpending")
public class UserAlbumPendingController {

    @Autowired
    private UserAlbumPendingFacade userAlbumPendingFacade;

    @PostMapping(path="/add")
    public UserAlbumPendingResponse addAlbumPending(@RequestBody UserAlbumPendingRequest request) {
        return userAlbumPendingFacade.addAlbumPending(request);
    }

    @DeleteMapping(path="/delete")
    public UserAlbumPendingResponse deleteAlbumPending(@RequestBody UserAlbumPendingRequest request) {
        return userAlbumPendingFacade.deleteAlbumPending(request);
    }

    @PutMapping(path="/update")
    public UserAlbumPendingResponse updateAlbumPending(@RequestBody UserAlbumPendingRequest request) {
        return userAlbumPendingFacade.updateAlbumPending(request);
    }

    @GetMapping(path="/find")
    public UserAlbumPendingResponse findAllAlbumsPending() {
        return userAlbumPendingFacade.findAllAlbumsPending();
    }
}
