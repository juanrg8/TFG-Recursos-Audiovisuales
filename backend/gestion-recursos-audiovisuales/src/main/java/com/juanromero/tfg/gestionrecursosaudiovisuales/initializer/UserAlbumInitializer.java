package com.juanromero.tfg.gestionrecursosaudiovisuales.initializer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album.Album;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.AlbumStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserAlbum;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.album.AlbumRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserAlbumRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;

@Component
public class UserAlbumInitializer implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private UserAlbumRepository userAlbumRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Verificar y crear usuarios si no existen
        Optional<User> user1Opt = userRepository.findByUsername("john_doe");
        User user1 = user1Opt.get();
        if (user1 == null) {
            user1 = new User();
            user1.setUsername("john_doe");
            user1.setNombre("John Doe");
            user1 = userRepository.save(user1);
        }

        Optional<User> user2Opt = userRepository.findByUsername("jane_smith");
        User user2 = user2Opt.get();
        if (user2 == null) {
            user2 = new User();
            user2.setUsername("jane_smith");
            user2.setNombre("Jane Smith");
            user2 = userRepository.save(user2);
        }

        // Verificar y crear álbumes si no existen
        Album album1 = albumRepository.findByTitle("Dark Side of the Moon");
        if (album1 == null) {
            album1 = new Album();
            album1.setTitle("Dark Side of the Moon");
            album1 = albumRepository.save(album1);
        }

        Album album2 = albumRepository.findByTitle("Abbey Road");
        if (album2 == null) {
            album2 = new Album();
            album2.setTitle("Abbey Road");
            album2 = albumRepository.save(album2);
        }

        // Verificar y crear relaciones UserAlbum si no existen
        if (!userAlbumRepository.existsByUsuarioAndAlbum(user1, album1)) {
            UserAlbum userAlbum1 = new UserAlbum();
            userAlbum1.setSpotifyId("0k17h0D3J5VfsdmQ1iZtE9");
            userAlbum1.setUsuario(user1);
            userAlbum1.setAlbum(album1);
            userAlbum1.setStatus(AlbumStatus.PENDING);
            userAlbum1.setReview("A classic album with amazing tracks.");
            userAlbum1.setRating(new BigDecimal("9.5"));
            userAlbumRepository.save(userAlbum1);
        }

        if (!userAlbumRepository.existsByUsuarioAndAlbum(user2, album1)) {
            UserAlbum userAlbum2 = new UserAlbum();
            userAlbum2.setSpotifyId("0hIG9FXgjQxT8fKaYceFbA");
            userAlbum2.setUsuario(user2);
            userAlbum2.setAlbum(album1);
            userAlbum2.setStatus(AlbumStatus.CONSUMED);
            userAlbum2.setReview("Timeless music.");
            userAlbum2.setRating(new BigDecimal("10.0"));
            userAlbum2.setDateListened(LocalDate.now());
            userAlbumRepository.save(userAlbum2);
        }

        if (!userAlbumRepository.existsByUsuarioAndAlbum(user1, album2)) {
            UserAlbum userAlbum3 = new UserAlbum();
            userAlbum3.setUsuario(user1);
            userAlbum3.setAlbum(album2);
            userAlbum3.setStatus(AlbumStatus.PENDING);
            userAlbum3.setReview("Great album from The Beatles.");
            userAlbum3.setRating(new BigDecimal("9.0"));
            userAlbumRepository.save(userAlbum3);
        }
    }
}
