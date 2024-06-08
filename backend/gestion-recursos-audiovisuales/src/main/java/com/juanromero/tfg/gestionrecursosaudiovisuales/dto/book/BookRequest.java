package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.book;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequest {

    private Integer id;
    private String title;
    private Integer pages;
    private LocalDate publishDate;
    private String author;
    private String genre;

    public BookRequest(Integer id, String title, Integer pages, LocalDate publishDate, String author, String genre) {
        super();
        this.id = id;
        this.title = title;
        this.pages = pages;
        this.publishDate = publishDate;
        this.author = author;
        this.genre = genre;
    }

	public BookRequest() {
	}
}
