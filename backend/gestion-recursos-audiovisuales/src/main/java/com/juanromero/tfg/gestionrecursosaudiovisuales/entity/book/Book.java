package com.juanromero.tfg.gestionrecursosaudiovisuales.entity.book;


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
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="book")
public class Book {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Title cannot be blank")
    @Column(name = "title")
    private String title;

    @PositiveOrZero(message = "Number of pages must be positive or zero")
    @Column(name = "pages")
    private Integer pages;

    @NotNull(message = "Publish date cannot be null")
    @Column(name = "publish_date")
    private LocalDate publishDate;

    @NotBlank(message = "Author cannot be blank")
    @Column(name = "author")
    private String author;

    @NotBlank(message = "Genre cannot be blank")
    @Column(name = "genre")
    private String genre;
	
	
}
