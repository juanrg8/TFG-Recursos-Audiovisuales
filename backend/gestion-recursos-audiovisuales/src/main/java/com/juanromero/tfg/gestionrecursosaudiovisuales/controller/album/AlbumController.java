package com.juanromero.tfg.gestionrecursosaudiovisuales.controller.album;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping(path="/{albumId}/addsong")
    public AlbumResponse addSongToAlbum(@PathVariable Integer albumId, @RequestBody String song) {
        return albumFacade.addSongToAlbum(albumId, song);
    }

    @DeleteMapping(path="/{albumId}/deletesong")
    public AlbumResponse deleteSongFromAlbum(@PathVariable Integer albumId, @RequestBody String song) {
        return albumFacade.deleteSongFromAlbum(albumId, song);
    }

    @PutMapping(path="/{albumId}/updatesong")
    public AlbumResponse updateSongInAlbum(@PathVariable Integer albumId, @RequestBody String[] songs) {
        String oldSong = songs[0];
        String newSong = songs[1];
        return albumFacade.updateSongInAlbum(albumId, oldSong, newSong);
    }

    @DeleteMapping(path="/deletealbum")
    public AlbumResponse deleteAlbum(@RequestBody AlbumRequest request) {
        return albumFacade.deleteAlbum(request);
    }

    @PutMapping(path="/updatealbum")
    public AlbumResponse updateAlbum(@RequestBody AlbumRequest request) {
        return albumFacade.updateAlbum(request);
    }

    @GetMapping(path="/findalbums")
    public AlbumResponse findAllAlbums() {
        return albumFacade.findAlbums();
    }
}

