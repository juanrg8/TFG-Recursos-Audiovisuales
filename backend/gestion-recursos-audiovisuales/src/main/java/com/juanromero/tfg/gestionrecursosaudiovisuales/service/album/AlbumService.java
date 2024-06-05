package com.juanromero.tfg.gestionrecursosaudiovisuales.service.album;

import java.util.List; 

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album.Album;



public interface AlbumService {

    String addAlbum(Album album);
    
    String deleteAlbum(Album album);
    
    String updateAlbum(Album album);
    
    List<Album> findAllAlbums();
    
    Album findAlbumsByTitle(String title);
    
    List<Album> findAlbumsByGenre(String musicGenre);
    
    List<Album> findAlbumsByArtist(String artist);
    
    List<Album> findAlbumsByPublishDate(String releaseDate);
    
    List<Album> findAlbumsBySong(String song);

    String addSongToAlbum(Integer albumId, String song);

    String deleteSongFromAlbum(Integer albumId, String song);

    String updateSongInAlbum(Integer albumId, String oldSong, String newSong);
}




