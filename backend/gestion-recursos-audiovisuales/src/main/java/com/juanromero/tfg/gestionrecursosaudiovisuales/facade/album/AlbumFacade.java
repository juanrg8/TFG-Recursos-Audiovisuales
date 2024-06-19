package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.album;


import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.album.AlbumRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.album.AlbumResponse;

public interface AlbumFacade {
    
    AlbumResponse addAlbum(AlbumRequest albumRequest);
    
    AlbumResponse deleteAlbum(AlbumRequest albumRequest);
    
    AlbumResponse updateAlbum(AlbumRequest albumRequest);
    
    AlbumResponse findAllAlbums();
    
    AlbumResponse findAlbumsByTitle(String title);
    
    AlbumResponse findAlbumsByArtist(String artist);
    
    AlbumResponse findAlbumsByPublishDate(String releaseDate);
    

}


