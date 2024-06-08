package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.VideogameStatus;

public class UserVideogameRequest {

	private Integer id;
	private Integer userId;
	private Integer videogameId;
	private VideogameStatus status;
	private String review;
	private BigDecimal rating;
	private LocalDate dateStarted;
	private LocalDate datePlayed;

	public UserVideogameRequest(Integer userId, Integer videogameId, VideogameStatus status, String review,
			BigDecimal rating, LocalDate dateStarted, LocalDate datePlayed) {
		super();
		this.userId = userId;
		this.videogameId = videogameId;
		this.status = status;
		this.review = review;
		this.rating = rating;
		this.dateStarted = dateStarted;
		this.datePlayed = datePlayed;
	}

	public UserVideogameRequest() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getVideogameId() {
		return videogameId;
	}

	public void setVideogameId(Integer videogameId) {
		this.videogameId = videogameId;
	}

	public VideogameStatus getStatus() {
		return status;
	}

	public void setStatus(VideogameStatus status) {
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

	public LocalDate getDateStarted() {
		return dateStarted;
	}

	public void setDateStarted(LocalDate dateStarted) {
		this.dateStarted = dateStarted;
	}

	public LocalDate getDatePlayed() {
		return datePlayed;
	}

	public void setDatePlayed(LocalDate datePlayed) {
		this.datePlayed = datePlayed;
	}

}
