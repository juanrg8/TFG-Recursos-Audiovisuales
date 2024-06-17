package com.juanromero.tfg.gestionrecursosaudiovisuales.entity.book;


import java.time.LocalDate; 

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
    @Column(name = "title", unique=true)
    private String title;

    @Column(name = "pages")
    private Integer pages;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @Column(name = "author")
    private String author;

    @Column(name = "genre")
    private String genre;
	
	
}
