package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.album;

import java.util.List; 


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumRequest {

    private Integer id;
    private String title;
    private Integer publishDate;
    private String artist;
    private String musicGenre;
    private List<String> songTrack;

    public AlbumRequest(Integer id, String title, Integer publishDate, String artist, String musicGenre, List<String> songTrack) {
        super();
        this.id = id;
        this.title = title;
        this.publishDate = publishDate;
        this.artist = artist;
        this.musicGenre = musicGenre;
        this.songTrack = songTrack;
    }
}

