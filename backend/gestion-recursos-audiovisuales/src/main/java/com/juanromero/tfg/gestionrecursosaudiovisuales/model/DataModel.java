package com.juanromero.tfg.gestionrecursosaudiovisuales.model;

import java.util.List;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album.Album;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.book.Book;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataModel {

    private List<User> usuarios;
    private List<Book> libros;
    private List<Album> albums;

}