package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.book;

import java.util.Date; 


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequest {

    private Integer id;
    private String title;
    private Date publishDate;
    private String author;
    private String genre;

    public BookRequest(Integer id, String title, Date publishDate, String author, String genre) {
        super();
        this.id = id;
        this.title = title;
        this.publishDate = publishDate;
        this.author = author;
        this.genre = genre;
    }
}
