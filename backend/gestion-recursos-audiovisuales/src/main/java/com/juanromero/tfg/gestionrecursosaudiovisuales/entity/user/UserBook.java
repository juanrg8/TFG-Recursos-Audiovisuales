package com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.book.Book;

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
@Table(name = "userbook")
public class UserBook {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @NotNull(message = "User cannot be null")
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @NotNull(message = "Book cannot be null")
    private Book book;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @NotNull(message = "Status cannot be null")
    private BookStatus status;

    @Column(name = "review")
    @Size(max = 255, message = "Review must be less than 255 characters")
    private String review;

    @Column(name = "rating")
    private BigDecimal rating;

    @Column(name = "date_started")
    private LocalDate dateStarted;

    @Column(name = "date_read")
    private LocalDate dateRead;

	// Getters y setters
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

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
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
