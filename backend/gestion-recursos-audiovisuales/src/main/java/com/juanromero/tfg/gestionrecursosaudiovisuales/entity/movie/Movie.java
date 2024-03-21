package com.juanromero.tfg.gestionrecursosaudiovisuales.entity.movie;

import java.time.LocalDate; 


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    private MovieGenre genre;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "director")
    private String director;

    @Column(name = "studio")
    private String studio;

    public Movie() {
    }

    public Movie(String title, MovieGenre genre, LocalDate releaseDate, String director, String studio) {
        this.title = title;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.director = director;
        this.studio = studio;
    }

}
