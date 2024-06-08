package com.juanromero.tfg.gestionrecursosaudiovisuales.initializer;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.videogame.Videogame;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.VideogameStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserVideogame;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.videogame.VideogameRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserVideogameRepository;

import org.springframework.boot.ApplicationArguments;

@Component
public class UserVideogameInitializer implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideogameRepository videogameRepository;

    @Autowired
    private UserVideogameRepository userVideogameRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Verificar y crear usuarios si no existen
        User user1 = userRepository.findByUsername("john_doe");
        if (user1 == null) {
            user1 = new User();
            user1.setUsername("john_doe");
            user1.setNombre("John Doe");
            user1 = userRepository.save(user1);
        }

        User user2 = userRepository.findByUsername("jane_smith");
        if (user2 == null) {
            user2 = new User();
            user2.setUsername("jane_smith");
            user2.setNombre("Jane Smith");
            user2 = userRepository.save(user2);
        }

        // Verificar y crear videojuegos si no existen
        Videogame game1 = videogameRepository.findByTitle("The Legend of Zelda: Breath of the Wild");
        if (game1 == null) {
            game1 = new Videogame();
            game1.setTitle("The Legend of Zelda: Breath of the Wild");
            game1.setPlatform("Nintendo Switch");
            game1.setGenre("Action Adventure");
            game1.setReleaseDate(LocalDate.parse("2017-03-03"));
            game1.setDeveloper("Nintendo EPD");
            game1.setPublisher("Nintendo");
            videogameRepository.save(game1);
        }

        Videogame game2 = videogameRepository.findByTitle("The Witcher 3: Wild Hunt");
        if (game2 == null) {
            game2 = new Videogame();
            game2.setTitle("The Witcher 3: Wild Hunt");
            game2.setPlatform("PlayStation 4, Xbox One, Microsoft Windows, Nintendo Switch");
            game2.setGenre("Action RPG");
            game2.setReleaseDate(LocalDate.parse("2015-05-18"));
            game2.setDeveloper("CD Projekt Red");
            game2.setPublisher("CD Projekt");
            videogameRepository.save(game2);
        }

        // Verificar y crear relaciones UserVideogame si no existen
        if (!userVideogameRepository.existsByUsuarioAndVideogame(user1.getId(), game1.getId())) {
            UserVideogame userGame1 = new UserVideogame();
            userGame1.setUsuario(user1);
            userGame1.setVideogame(game1);
            userGame1.setStatus(VideogameStatus.PLAYED);
            userGame1.setReview("An epic adventure!");
            userGame1.setRating(new BigDecimal("9.5"));
            userGame1.setDateStarted(LocalDate.now().minusMonths(1));
            userGame1.setDatePlayed(LocalDate.now()); 
            userVideogameRepository.save(userGame1);
        }

        if (!userVideogameRepository.existsByUsuarioAndVideogame(user2.getId(), game2.getId())) {
            UserVideogame userGame2 = new UserVideogame();
            userGame2.setUsuario(user2);
            userGame2.setVideogame(game2);
            userGame2.setStatus(VideogameStatus.PLAYING);
            userGame2.setReview("A fantastic RPG!");
            userGame2.setRating(new BigDecimal("9.0"));
            userGame2.setDateStarted(LocalDate.now());
            userVideogameRepository.save(userGame2);
        }
    }
}
