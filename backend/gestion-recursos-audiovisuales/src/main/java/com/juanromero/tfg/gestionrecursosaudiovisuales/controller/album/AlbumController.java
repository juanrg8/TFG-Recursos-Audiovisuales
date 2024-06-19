package com.juanromero.tfg.gestionrecursosaudiovisuales.controller.album;

import java.util.List;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.album.AlbumRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.album.AlbumResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.album.AlbumFacade;

@RestController
@RequestMapping(path="/album")
public class AlbumController {

    @Autowired
    private AlbumFacade albumFacade;

    @PostMapping(path="/addalbum")
    public AlbumResponse addAlbum(@RequestBody AlbumRequest request) {
        return albumFacade.addAlbum(request);
    }


    @DeleteMapping(path="/deletealbum")
    public AlbumResponse deleteAlbum(@RequestBody AlbumRequest request) {
        return albumFacade.deleteAlbum(request);
    }

    @PutMapping(path="/updatealbum")
    public AlbumResponse updateAlbum(@RequestBody AlbumRequest request) {
        return albumFacade.updateAlbum(request);
    }

    @GetMapping(path="/all")
    public List<AlbumRequest> findAllAlbums() {
        return albumFacade.findAllAlbums().getListaAlbumes();
    }
    
    @GetMapping(path="/title")
    public List<AlbumRequest> findAlbumsByTitle(@RequestBody AlbumRequest request) {
        return albumFacade.findAlbumsByTitle(request.getTitle()).getListaAlbumes();
    }
    
    
    @GetMapping(path="/artist")
    public List<AlbumRequest> findAlbumsByArtist(@RequestBody AlbumRequest request) {
        return albumFacade.findAlbumsByArtist(request.getArtist()).getListaAlbumes();
    }
    
    @GetMapping(path="/date")
    public List<AlbumRequest> findAlbumsByPublishDate(@RequestBody AlbumRequest request) {
        return albumFacade.findAlbumsByPublishDate(request.getPublishDate().toString()).getListaAlbumes();
    }
    
}

