package com.juanromero.tfg.gestionrecursosaudiovisuales.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserAlbumConsumedRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserAlbumConsumedResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user.UserAlbumConsumedFacade;

@RestController
@RequestMapping(path="/user/albumconsumed")
public class UserAlbumConsumedController {

    @Autowired
    private UserAlbumConsumedFacade userAlbumConsumedFacade;

    @PostMapping(path="/add")
    public UserAlbumConsumedResponse addAlbumConsumed(@RequestBody UserAlbumConsumedRequest request) {
        return userAlbumConsumedFacade.addAlbumConsumed(request);
    }

    @DeleteMapping(path="/delete")
    public UserAlbumConsumedResponse deleteAlbumConsumed(@RequestBody UserAlbumConsumedRequest request) {
        return userAlbumConsumedFacade.deleteAlbumConsumed(request);
    }

    @PutMapping(path="/update")
    public UserAlbumConsumedResponse updateAlbumConsumed(@RequestBody UserAlbumConsumedRequest request) {
        return userAlbumConsumedFacade.updateAlbumConsumed(request);
    }

    @GetMapping(path="/find")
    public UserAlbumConsumedResponse findAllAlbumsConsumed() {
        return userAlbumConsumedFacade.findAllAlbumsConsumed();
    }
}
