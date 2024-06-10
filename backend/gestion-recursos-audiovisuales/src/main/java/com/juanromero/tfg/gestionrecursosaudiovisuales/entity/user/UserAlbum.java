package com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user;

import java.math.BigDecimal;
import java.time.LocalDate;

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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
@Table(name="useralbum")
public class UserAlbum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @NotNull(message = "User cannot be null")
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "album_id")
    @NotNull(message = "Album cannot be null")
    private Album album;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @NotNull(message = "Status cannot be null")
    private AlbumStatus status;

    @Column(name = "review")
    @Size(max = 255, message = "Review must be less than 255 characters")
    private String review;

    @Column(name = "rating")
    private BigDecimal rating;

    @Column(name = "date_listened")
    private LocalDate dateListened;

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

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	public LocalDate getDateListened() {
		return dateListened;
	}

	public void setDateListened(LocalDate dateListened) {
		this.dateListened = dateListened;
	}
    
}
