package com.juanromero.tfg.gestionrecursosaudiovisuales.entity.videogame;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "title")
    private String title;

    @Column(name = "platform")
    private String platform;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private VideogameGenre genre;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "developer")
    private String developer;

    @Column(name = "publisher")
    private String publisher;

    public Videogame() {
    }
}
