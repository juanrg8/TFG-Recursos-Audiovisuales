package com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user;

//import com.fasterxml.jackson.annotation.JsonIgnore; 
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album.Album; 

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="useralbum")
public class UserAlbum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;
    
    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AlbumStatus status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public AlbumStatus getStatus() {
		return status;
	}

	public void setStatus(AlbumStatus status) {
		this.status = status;
	}
    
    
    
}
