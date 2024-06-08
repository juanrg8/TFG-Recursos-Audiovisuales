package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.movie;

import java.time.LocalDate;

public class MovieRequest {

	private Integer id;

	private String title;
	
	private Integer duration;

	private String genre;

	private LocalDate releaseDate;

	private String director;

	private String studio;

	public MovieRequest() {
	}

	public MovieRequest(String title, Integer duration, String genre, LocalDate releaseDate, String director, String studio) {
		this.title = title;
		this.duration = duration;
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
