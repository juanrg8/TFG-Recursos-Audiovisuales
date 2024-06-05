package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.book;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequest {

    private Integer id;
    private String title;
    private LocalDate publishDate;
    private String author;
    private String genre;

    public BookRequest(Integer id, String title, LocalDate publishDate, String author, String genre) {
        super();
        this.id = id;
        this.title = title;
        this.publishDate = publishDate;
        this.author = author;
        this.genre = genre;
    }

	public BookRequest() {
	}
}
