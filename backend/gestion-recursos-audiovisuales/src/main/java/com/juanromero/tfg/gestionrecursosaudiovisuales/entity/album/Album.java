package com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album;


import java.time.LocalDate;  

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
    @Column(name = "title",unique = true)
    private String title;

    @Column(name = "tracks")
    private Integer tracks;

    @Column(name = "artist")
    private String artist;

    @Column(name = "publish_date")
    private LocalDate publishDate;
    
    public Album(Integer id) {
        this.id = id;
    }
    
    
    public Album() {

    }
}
