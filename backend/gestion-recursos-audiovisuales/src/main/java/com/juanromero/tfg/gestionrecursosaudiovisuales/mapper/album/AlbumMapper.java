package com.juanromero.tfg.gestionrecursosaudiovisuales.mapper.album;

import org.springframework.stereotype.Service; 

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.album.AlbumRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album.Album;

@Service
public class AlbumMapper {

    public Album dtoToEntity(AlbumRequest dto) {
        Album entity = new Album();

        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setArtist(dto.getArtist());
        entity.setPublishDate(dto.getPublishDate());
        entity.setMusicGenre(dto.getMusicGenre());
        entity.setSongTrack(dto.getSongTrack());

        return entity;
    }
}

