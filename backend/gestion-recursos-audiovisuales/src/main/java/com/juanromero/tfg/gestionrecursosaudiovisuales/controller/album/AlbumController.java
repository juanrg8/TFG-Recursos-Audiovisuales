package com.juanromero.tfg.gestionrecursosaudiovisuales.controller.album;

import java.util.List; 
import java.util.Map; 

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
    public AlbumResponse addSongToAlbum(@PathVariable Integer albumId, @RequestBody Map<String, String> requestBody) {
        String songTitle = requestBody.get("songTitle");
        return albumFacade.addSongToAlbum(albumId, songTitle);
    }

    @DeleteMapping(path="/{albumId}/deletesong")
    public AlbumResponse deleteSongFromAlbum(@PathVariable Integer albumId, @RequestBody Map<String, String> requestBody) {
        String songTitle = requestBody.get("songTitle");
        return albumFacade.deleteSongFromAlbum(albumId, songTitle);
    }

    @PutMapping(path="/{albumId}/updatesong")
    public AlbumResponse updateSongInAlbum(@PathVariable Integer albumId, @RequestBody Map<String, String> requestBody) {
        String oldSongTitle = requestBody.get("oldSongTitle");
        String newSongTitle = requestBody.get("newSongTitle");
        return albumFacade.updateSongInAlbum(albumId, oldSongTitle, newSongTitle);
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
    
    @GetMapping(path="/genre")
    public List<AlbumRequest> findAlbumsByGenre(@RequestBody AlbumRequest request) {
        return albumFacade.findAlbumsByGenre(request.getMusicGenre()).getListaAlbumes();
    }
    
    @GetMapping(path="/artist")
    public List<AlbumRequest> findAlbumsByArtist(@RequestBody AlbumRequest request) {
        return albumFacade.findAlbumsByArtist(request.getArtist()).getListaAlbumes();
    }
    
    @GetMapping(path="/date")
    public List<AlbumRequest> findAlbumsByPublishDate(@RequestBody AlbumRequest request) {
        return albumFacade.findAlbumsByPublishDate(request.getPublishDate().toString()).getListaAlbumes();
    }
    
    @GetMapping(path="/song")
    public List<AlbumRequest> findAlbumsBySong(@RequestBody AlbumRequest request) {
        return albumFacade.findAlbumsBySong(request.getSongTrack().get(0)).getListaAlbumes();
    }
}

