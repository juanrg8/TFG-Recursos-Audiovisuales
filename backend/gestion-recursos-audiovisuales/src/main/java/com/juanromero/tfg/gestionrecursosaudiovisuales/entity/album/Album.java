package com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album;


import java.util.List;


import jakarta.persistence.JoinColumn;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "artist")
    private String artist;
    
    @Column(name = "release_year")
    private Integer publishDate;
    
    @Column(name = "genre")
    private MusicGenre musicGenre;
    
    @ElementCollection
    @CollectionTable(name="tracks", joinColumns=@JoinColumn(name="album_id"))
    @Column(name="track")
    private List<String> songTrack;
    
    public Album() {

    }
}
