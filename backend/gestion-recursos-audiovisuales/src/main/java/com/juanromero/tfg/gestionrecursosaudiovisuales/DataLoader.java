package com.juanromero.tfg.gestionrecursosaudiovisuales;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album.Album;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.book.Book;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.movie.Movie;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserAlbum;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.videogame.Videogame;
import com.juanromero.tfg.gestionrecursosaudiovisuales.model.DataModel;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.album.AlbumRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.book.BookRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.movie.MovieRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserAlbumRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.videogame.VideogameRepository;

@Component
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final AlbumRepository albumRepository;
    private final VideogameRepository videogameRepository;
    private final MovieRepository movieRepository;
    private final UserAlbumRepository userAlbumRepository;

    public DataLoader(UserRepository userRepository, BookRepository bookRepository, AlbumRepository albumRepository,
                      VideogameRepository videogameRepository, MovieRepository movieRepository, UserAlbumRepository userAlbumRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.albumRepository = albumRepository;
        this.videogameRepository = videogameRepository;
        this.movieRepository = movieRepository;
        this.userAlbumRepository = userAlbumRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        DataModel data = objectMapper.readValue(new File("src/main/resources/initial_data.json"), DataModel.class);

        List<User> users = data.getUsuarios();
        List<Book> books = data.getLibros();
        List<Album> albums = data.getAlbums();
        List<Videogame> videogames = data.getVideojuegos();
        List<Movie> movies = data.getPeliculas();
        List<UserAlbum> userAlbums = data.getUserAlbums();

        if (users != null) {
            for (User user : users) {
                User existingUser = userRepository.findByUsername(user.getUsername());
                if (existingUser == null) {
                    userRepository.save(user);
                }
            }
        }

        if (books != null) {
            for (Book book : books) {
                Book existingBook = bookRepository.findByTitle(book.getTitle());
                if (existingBook == null) {
                    bookRepository.save(book);
                }
            }
        }

        if (albums != null) {
            for (Album album : albums) {
                Album existingAlbum = albumRepository.findByTitle(album.getTitle());
                if (existingAlbum == null) {
                    albumRepository.save(album);
                }
            }
        }

        if (videogames != null) {
            for (Videogame videogame : videogames) {
                Videogame existingVideogame = videogameRepository.findByTitle(videogame.getTitle());
                if (existingVideogame == null) {
                    videogameRepository.save(videogame);
                }
            }
        }

        if (movies != null) {
            for (Movie movie : movies) {
                Movie existingMovie = movieRepository.findByTitle(movie.getTitle());
                if (existingMovie == null) {
                    movieRepository.save(movie);
                }
            }
        }

        if (userAlbums != null) {
            for (UserAlbum userAlbum : userAlbums) {
                Optional<UserAlbum> existingUserAlbum = userAlbumRepository.findByUsuarioIdAndAlbumId(userAlbum.getUsuario().getId(), userAlbum.getAlbum().getId());
                if (!existingUserAlbum.isPresent()) {
                    userAlbumRepository.save(userAlbum);
                }
            }
        }
    }
}