package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user;

import java.math.BigDecimal; 
import java.time.LocalDate;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.BookStatus;

public class UserBookRequest {

	private Integer id;
	private String libroId;
	private String tituloLibro;
	private Integer paginas;
	private LocalDate publishDate;
	private String author;
	private String genre;
	private String usuarioNombre;
	private Integer bookId;
	private BookStatus status;
	private String review;
	private BigDecimal rating;
	private LocalDate dateStarted;
	private LocalDate dateRead;

	public UserBookRequest(Integer id, String libroId, String usuarioNombre, Integer bookId, BookStatus status,
			String review, BigDecimal rating, LocalDate dateStarted, LocalDate dateRead) {
		this.id = id;
		this.libroId = libroId;
		this.usuarioNombre = usuarioNombre;
		this.bookId = bookId;
		this.status = status;
		this.review = review;
		this.rating = rating;
		this.dateStarted = dateStarted;
		this.dateRead = dateRead;
	}

	public UserBookRequest() {
	}

	public UserBookRequest(String libroId, BookStatus status, String usuarioNombre, String tituloLibro, Integer paginas, LocalDate publishDate, String author, String genre) {
		this.libroId = libroId;
		this.status = status;
		this.usuarioNombre = usuarioNombre;
		this.tituloLibro = tituloLibro;
		this.paginas = paginas;
		this.publishDate = publishDate;
		this.author = author;
		this.genre = genre;
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLibroId() {
		return libroId;
	}

	public void setLibroId(String libroId) {
		this.libroId = libroId;
	}

	public String getTituloLibro() {
		return tituloLibro;
	}

	public Integer getPaginas() {
		return paginas;
	}

	public void setPaginas(Integer paginas) {
		this.paginas = paginas;
	}

	public LocalDate getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(LocalDate publishDate) {
		this.publishDate = publishDate;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setTituloLibro(String tituloLibro) {
		this.tituloLibro = tituloLibro;
	}

	public String getUsuarioNombre() {
		return usuarioNombre;
	}

	public void setUsuarioNombre(String usuarioNombre) {
		this.usuarioNombre = usuarioNombre;
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
