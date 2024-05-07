package com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album.Album;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="useralbumconsumed")
public class UserAlbumConsumed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

}
