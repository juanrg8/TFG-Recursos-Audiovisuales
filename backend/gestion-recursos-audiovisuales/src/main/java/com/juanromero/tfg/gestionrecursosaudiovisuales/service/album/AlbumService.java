package com.juanromero.tfg.gestionrecursosaudiovisuales.service.album;

import java.util.List; 

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album.Album;



public interface AlbumService {

    String addAlbum(Album album);
    
    String deleteAlbum(Album album);
    
    String updateAlbum(Album album);
    
    List<Album> findAllAlbums();
    
    Album findAlbumsByTitle(String title);
    
    List<Album> findAlbumsByArtist(String artist);
    
    List<Album> findAlbumsByPublishDate(String releaseDate);
    

}




