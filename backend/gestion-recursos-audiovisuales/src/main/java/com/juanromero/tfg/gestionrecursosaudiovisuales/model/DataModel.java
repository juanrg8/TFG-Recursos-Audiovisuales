package com.juanromero.tfg.gestionrecursosaudiovisuales.model;

import java.util.List;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album.Album;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.book.Book;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.movie.Movie;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserAlbum;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.videogame.Videogame;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataModel {

    private List<User> usuarios;
    private List<Book> libros;
    private List<Album> albums;
    private List<Videogame> videojuegos;
    private List<Movie> peliculas;
    private List<UserAlbum> userAlbums;
    

}