package com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album;


import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="album")
public class Album {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    

    @NotBlank(message = "Title cannot be blank")
    @Column(name = "title")
    private String title;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "artist")
    private String artist;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @Column(name = "genre")
    private String musicGenre;

    @ElementCollection
    @CollectionTable(name = "tracks", joinColumns = @JoinColumn(name = "album_id"))
    @Column(name = "track")
    private List<String> songTrack;
    
    public Album(Integer id) {
        this.id = id;
    }
    
    
    public Album() {

    }
}
