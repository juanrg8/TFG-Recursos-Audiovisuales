package com.juanromero.tfg.gestionrecursosaudiovisuales;

import java.io.File;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album.Album;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.book.Book;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.model.DataModel;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.album.AlbumRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.book.BookRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;

@Component
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final AlbumRepository albumRepository;

    public DataLoader(UserRepository userRepository, BookRepository bookRepository, AlbumRepository albumRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.albumRepository = albumRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        DataModel data = objectMapper.readValue(new File("src/main/resources/initial_data.json"), DataModel.class);

        List<User> users = data.getUsuarios();
        List<Book> books = data.getLibros();
        List<Album> albums = data.getAlbums();

        for (User user : users) {
            User existingUser = userRepository.findByUsername(user.getUsername());
            if (existingUser == null) {
                userRepository.save(user);
            }
        }

        for (Book book : books) {
            Book existingBook = bookRepository.findByTitle(book.getTitle());
            if (existingBook == null) {
                bookRepository.save(book);
            }
        }

        for (Album album : albums) {
            Album existingAlbum = albumRepository.findByTitle(album.getTitle());
            if (existingAlbum == null) {
                albumRepository.save(album);
            }
        }
    }
}

