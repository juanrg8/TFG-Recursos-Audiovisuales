package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.album.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.album.AlbumRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.album.AlbumResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album.Album;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.album.AlbumFacade;
import com.juanromero.tfg.gestionrecursosaudiovisuales.mapper.album.AlbumMapper;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.album.AlbumService;

@Service
public class AlbumFacadeImpl implements AlbumFacade {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumMapper albumMapper;

    private static final String response_guardado_ok = "Álbum guardado con éxito";
    private static final String response_borrado_ok = "El álbum se ha borrado correctamente.";
    private static final String response_actualizado_ok = "El álbum se ha actualizado correctamente.";
    private static final String response_cancion_guardada_ok = "Canción agregada al álbum correctamente.";
    private static final String response_cancion_borrada_ok = "Canción eliminada del álbum correctamente.";
    private static final String response_cancion_actualizada_ok = "Canción actualizada en el álbum correctamente.";
    private static final String response_encontrado_ok = "Álbumes encontrados correctamente.";

    @Override
    public AlbumResponse addAlbum(AlbumRequest albumRequest) {
        AlbumResponse response = new AlbumResponse();
        String descripcionPeticion = "";

        Album album = albumMapper.dtoToEntity(albumRequest);
        descripcionPeticion = albumService.addAlbum(album);
        response.setDescripcionPeticion(descripcionPeticion);

        if (descripcionPeticion.equalsIgnoreCase(response_guardado_ok)) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }

    @Override
    public AlbumResponse deleteAlbum(AlbumRequest albumRequest) {
        AlbumResponse response = new AlbumResponse();
        String descripcionPeticion = "";

        Album album = albumMapper.dtoToEntity(albumRequest);
        descripcionPeticion = albumService.deleteAlbum(album);
        response.setDescripcionPeticion(descripcionPeticion);

        if (descripcionPeticion.equalsIgnoreCase(response_borrado_ok)) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }

    @Override
    public AlbumResponse updateAlbum(AlbumRequest albumRequest) {
        AlbumResponse response = new AlbumResponse();
        String descripcionPeticion = "";

        Album album = albumMapper.dtoToEntity(albumRequest);
        descripcionPeticion = albumService.updateAlbum(album);
        response.setDescripcionPeticion(descripcionPeticion);

        if (descripcionPeticion.equalsIgnoreCase(response_actualizado_ok)) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }

    @Override
    public AlbumResponse findAlbums() {
        AlbumResponse response = new AlbumResponse();
        String descripcionPeticion = "No se han encontrado álbumes";

        List<Album> lista = albumService.findAllAlbums();
        response.setListaAlbumes(lista);;
        if (lista.isEmpty()) {
            response.setEstadoPeticion("KO");
            response.setDescripcionPeticion(descripcionPeticion);
        } else {
            response.setEstadoPeticion("OK");
            response.setDescripcionPeticion(response_encontrado_ok);
        }

        return response;
    }
    
    //TODO: no añadir canciones repetidas
    @Override
    public AlbumResponse addSongToAlbum(Integer albumId, String song) {
        AlbumResponse response = new AlbumResponse();
        String descripcionPeticion = "";

        descripcionPeticion = albumService.addSongToAlbum(albumId, song);
        response.setDescripcionPeticion(descripcionPeticion);

        if (descripcionPeticion.equalsIgnoreCase(response_cancion_guardada_ok)) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }   
    
    //TODO: no borrar canciones que no están
    @Override
    public AlbumResponse deleteSongFromAlbum(Integer albumId, String song) {
        AlbumResponse response = new AlbumResponse();
        String descripcionPeticion = "";

        descripcionPeticion = albumService.deleteSongFromAlbum(albumId, song);
        response.setDescripcionPeticion(descripcionPeticion);

        if (descripcionPeticion.equalsIgnoreCase(response_cancion_borrada_ok)) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }
    
    //TODO: no actualizar canciones que no están
    @Override
    public AlbumResponse updateSongInAlbum(Integer albumId, String oldSong, String newSong) {
        AlbumResponse response = new AlbumResponse();
        String descripcionPeticion = "";

        descripcionPeticion = albumService.updateSongInAlbum(albumId, oldSong, newSong);
        response.setDescripcionPeticion(descripcionPeticion);

        if (descripcionPeticion.equalsIgnoreCase(response_cancion_actualizada_ok)) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }
}
