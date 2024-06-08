package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.AlbumStatus;

public class UserAlbumRequest {

	private Integer id;
	private Integer usuarioId;
	private Integer albumId;
	private AlbumStatus status;
	private String review;
	private BigDecimal rating;
	private LocalDate fechaCambioEstado;

	public UserAlbumRequest(Integer id, Integer usuarioId, Integer albumId, AlbumStatus status, String review,
			BigDecimal rating, LocalDate fechaCambioEstado) {
		this.id = id;
		this.usuarioId = usuarioId;
		this.albumId = albumId;
		this.status = status;
		this.review = review;
		this.rating = rating;
		this.fechaCambioEstado = fechaCambioEstado;
	}

	public UserAlbumRequest() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
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
