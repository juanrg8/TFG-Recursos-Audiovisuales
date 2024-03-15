package com.juanromero.tfg.gestionrecursosaudiovisuales;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.book.Book;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.model.DataModel;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.book.BookRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.io.File;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    private final UserRepository usuarioRepository;
    private final BookRepository libroRepository;

    public DataLoader(UserRepository usuarioRepository, BookRepository libroRepository) {
        this.usuarioRepository = usuarioRepository;
        this.libroRepository = libroRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        DataModel data = objectMapper.readValue(new File("src/main/resources/initial_data.json"), DataModel.class);

        List<User> usuarios = data.getUsuarios();
        List<Book> libros = data.getLibros();

        for (User usuario : usuarios) {
            User existingUser = usuarioRepository.findByUsername(usuario.getUsername());
            if (existingUser == null) {
                usuarioRepository.save(usuario);
            }
        }

        for (Book libro : libros) {
            Book existingLibro = libroRepository.findByTitle(libro.getTitle());
            if (existingLibro == null) {
                libroRepository.save(libro);
            }
        }

    }
}

