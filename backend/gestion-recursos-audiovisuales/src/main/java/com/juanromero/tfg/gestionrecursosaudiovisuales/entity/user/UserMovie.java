package com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.movie.Movie;

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
@Table(name = "usermovie")
public class UserMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @NotNull(message = "User cannot be null")
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @NotNull(message = "Movie cannot be null")
    private Movie movie;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @NotNull(message = "Status cannot be null")
    private MovieStatus status;

    @Column(name = "review")
    @Size(max = 255, message = "Review must be less than 255 characters")
    private String review;

    @Column(name = "rating")
    private BigDecimal rating;

    @Column(name = "date_watched")
    private LocalDate dateWatched;

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

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
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
