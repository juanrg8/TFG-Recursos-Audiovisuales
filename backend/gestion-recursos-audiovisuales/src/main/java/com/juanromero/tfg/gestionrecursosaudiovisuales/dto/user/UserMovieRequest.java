package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.MovieStatus;

public class UserMovieRequest {

	private Integer id;
	private Integer userId;
	private Integer movieId;
	private MovieStatus status;
	private String review;
	private BigDecimal rating;
	private LocalDate dateWatched;

	public UserMovieRequest(Integer userId, Integer movieId, MovieStatus status, String review,
			BigDecimal rating, LocalDate dateWatched) {
		super();
		this.userId = userId;
		this.movieId = movieId;
		this.status = status;
		this.review = review;
		this.rating = rating;
		this.dateWatched = dateWatched;
	}

	public UserMovieRequest() {
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

	public Integer getMovieId() {
		return movieId;
	}

	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}

	public MovieStatus getStatus() {
		return status;
	}

	public void setStatus(MovieStatus status) {
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

	public LocalDate getDateWatched() {
		return dateWatched;
	}

	public void setDateWatched(LocalDate dateWatched) {
		this.dateWatched = dateWatched;
	}

}
