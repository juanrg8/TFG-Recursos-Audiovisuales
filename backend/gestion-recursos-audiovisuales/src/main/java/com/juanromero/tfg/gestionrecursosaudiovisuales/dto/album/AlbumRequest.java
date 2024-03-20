package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.album;

import java.util.List;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album.MusicGenre;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumRequest {

    private Integer id;
    private String title;
    private Integer publishDate;
    private String artist;
    private MusicGenre musicGenre;
    private List<String> songTrack;

    public AlbumRequest(Integer id, String title, Integer publishDate, String artist, MusicGenre musicGenre, List<String> songTrack) {
        super();
        this.id = id;
        this.title = title;
        this.publishDate = publishDate;
        this.artist = artist;
        this.musicGenre = musicGenre;
        this.songTrack = songTrack;
    }
}

