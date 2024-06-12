package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.AlbumStatus;

public class UserAlbumRequest {

	private Integer id;
	private String spotifyId;
	private String tituloAlbum;
	private String usuarioNombre;
	private Integer albumId;
	private AlbumStatus status;
	private String review;
	private BigDecimal rating;
	private LocalDate fechaCambioEstado;

	public UserAlbumRequest(Integer id, String spotifyId, String usuarioNombre, Integer albumId,
			AlbumStatus status, String review, BigDecimal rating, LocalDate fechaCambioEstado) {
		this.id = id;
		this.spotifyId = spotifyId;
		this.usuarioNombre = usuarioNombre;
		this.albumId = albumId;
		this.status = status;
		this.review = review;
		this.rating = rating;
		this.fechaCambioEstado = fechaCambioEstado;
	}

	public UserAlbumRequest() {

	}

	public UserAlbumRequest(String spotifyId, AlbumStatus status, String usuarioNombre, String tituloAlbum) {
		this.spotifyId = spotifyId;
		this.status = status;
		this.usuarioNombre = usuarioNombre;
		this.tituloAlbum = tituloAlbum;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSpotifyId() {
		return spotifyId;
	}

	public void setSpotifyId(String spotifyId) {
		this.spotifyId = spotifyId;
	}

	public String getTituloAlbum() {
		return tituloAlbum;
	}

	public void setTituloAlbum(String tituloAlbum) {
		this.tituloAlbum = tituloAlbum;
	}

	public String getUsuarioNombre() {
		return usuarioNombre;
	}

	public void setUsuarioNombre(String usuarioNombre) {
		this.usuarioNombre = usuarioNombre;
	}

	public Integer getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Integer albumId) {
		this.albumId = albumId;
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

	public LocalDate getFechaCambioEstado() {
		return fechaCambioEstado;
	}

	public void setFechaCambioEstado(LocalDate fechaCambioEstado) {
		this.fechaCambioEstado = fechaCambioEstado;
	}
}
