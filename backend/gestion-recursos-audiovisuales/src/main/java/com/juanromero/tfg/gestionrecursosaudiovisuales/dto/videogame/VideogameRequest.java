package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.videogame;


import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideogameRequest {

    private Integer id;
    private String title;
    private String platform;
    private String genre;
    private LocalDate releaseDate;
    private String developer;
    private String publisher;

    public VideogameRequest(Integer id, String title, String platform, String genre, LocalDate releaseDate, String developer, String publisher) {
        super();
        this.id = id;
        this.title = title;
        this.platform = platform;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.developer = developer;
        this.publisher = publisher;
    }
    
    public VideogameRequest() {

    }
}
