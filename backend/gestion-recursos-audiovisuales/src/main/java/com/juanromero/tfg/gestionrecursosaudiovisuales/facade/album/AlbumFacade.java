package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.album;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.album.AlbumRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.album.AlbumResponse;

public interface AlbumFacade {
    
    AlbumResponse addAlbum(AlbumRequest albumRequest);
    
    AlbumResponse deleteAlbum(AlbumRequest albumRequest);
    
    AlbumResponse updateAlbum(AlbumRequest albumRequest);
    
    AlbumResponse findAlbums();
    
    AlbumResponse addSongToAlbum(Integer albumId, String song);

    AlbumResponse deleteSongFromAlbum(Integer albumId, String song);

    AlbumResponse updateSongInAlbum(Integer albumId, String oldSong, String newSong);
}


