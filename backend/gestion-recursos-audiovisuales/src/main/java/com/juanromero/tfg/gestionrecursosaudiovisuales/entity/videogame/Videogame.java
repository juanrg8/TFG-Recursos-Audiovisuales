package com.juanromero.tfg.gestionrecursosaudiovisuales.entity.videogame;



import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "videogame")
public class Videogame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Title cannot be blank")
    @Column(name = "title")
    private String title;

    @NotBlank(message = "Platform cannot be blank")
    @Column(name = "platform")
    private String platform;

    @NotBlank(message = "Genre cannot be blank")
    @Column(name = "genre")
    private String genre;

    @NotNull(message = "Release date cannot be null")
    @Column(name = "release_date")
    private LocalDate releaseDate;

    @NotBlank(message = "Developer cannot be blank")
    @Column(name = "developer")
    private String developer;

    @NotBlank(message = "Publisher cannot be blank")
    @Column(name = "publisher")
    private String publisher;

    public Videogame() {
    }
}
