package com.juanromero.tfg.gestionrecursosaudiovisuales.entity.movie;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "movie")
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    @NotBlank(message = "Title cannot be blank")
    @Column(name = "title")
    private String title;

    @PositiveOrZero(message = "Duration must be positive or zero")
    @Column(name = "duration")
    private Integer duration;

    @NotBlank(message = "Genre cannot be blank")
    @Column(name = "genre")
    private String genre;

    @NotNull(message = "Release date cannot be null")
    @Column(name = "release_date")
    private LocalDate releaseDate;

    @NotBlank(message = "Director cannot be blank")
    @Column(name = "director")
    private String director;

    @NotBlank(message = "Studio cannot be blank")
    @Column(name = "studio")
    private String studio;

	public Movie() {
	}

	public Movie(String title, String genre, LocalDate releaseDate, String director, String studio) {
		this.title = title;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.director = director;
		this.studio = studio;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getStudio() {
		return studio;
	}

	public void setStudio(String studio) {
		this.studio = studio;
	}

}
