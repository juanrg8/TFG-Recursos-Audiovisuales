package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.BookStatus;

public class UserBookRequest {

	private Integer id;
	private Integer userId;
	private Integer bookId;
	private BookStatus status;
	private String review;
	private BigDecimal rating;
	private LocalDate dateStarted;
	private LocalDate dateRead;

	public UserBookRequest(Integer userId, Integer bookId, BookStatus status, String review, BigDecimal rating,
			LocalDate dateStarted, LocalDate dateRead) {
		super();
		this.userId = userId;
		this.bookId = bookId;
		this.status = status;
		this.review = review;
		this.rating = rating;
		this.dateStarted = dateStarted;
		this.dateRead = dateRead;
	}

	public UserBookRequest() {
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

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public BookStatus getStatus() {
		return status;
	}

	public void setStatus(BookStatus status) {
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

	public LocalDate getDateRead() {
		return dateRead;
	}

	public void setDateRead(LocalDate dateRead) {
		this.dateRead = dateRead;
	}

}
