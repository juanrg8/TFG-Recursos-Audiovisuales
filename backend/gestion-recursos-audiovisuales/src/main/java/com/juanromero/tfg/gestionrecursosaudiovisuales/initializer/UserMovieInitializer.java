package com.juanromero.tfg.gestionrecursosaudiovisuales.initializer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.movie.Movie;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.MovieStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserMovie;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.movie.MovieRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserMovieRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;

import org.springframework.boot.ApplicationArguments;

public class UserMovieInitializer implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserMovieRepository userMovieRepository;

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

        // Verificar y crear películas si no existen
        Movie movie1 = movieRepository.findByTitle("The Shawshank Redemption");
        if (movie1 == null) {
            movie1 = new Movie();
            movie1.setTitle("The Shawshank Redemption");
            movie1.setDuration(142);
            movie1.setDirector("Frank Darabont");
            movie1.setReleaseDate(LocalDate.parse("1994-09-22"));
            movie1.setGenre("Drama");
            movie1.setStudio("Castle Rock Entertainment");
            movieRepository.save(movie1);
        }

        Movie movie2 = movieRepository.findByTitle("The Godfather");
        if (movie2 == null) {
            movie2 = new Movie();
            movie2.setTitle("The Godfather");
            movie2.setDuration(175);
            movie2.setDirector("Francis Ford Coppola");
            movie2.setReleaseDate(LocalDate.parse("1972-03-24"));
            movie2.setGenre("Crime");
            movie2.setStudio("Paramount Pictures");
            movieRepository.save(movie2);
        }

        // Verificar y crear relaciones UserMovie si no existen
        if (!userMovieRepository.existsByUsuarioAndMovie(user1.getId(), movie1.getId())) {
            UserMovie userMovie1 = new UserMovie();
            userMovie1.setUsuario(user1);
            userMovie1.setMovie(movie1);
            userMovie1.setStatus(MovieStatus.WATCHED);
            userMovie1.setReview("A masterpiece!");
            userMovie1.setRating(new BigDecimal("10.0"));
            userMovie1.setDateWatched(LocalDate.now());
            userMovieRepository.save(userMovie1);
        }

        if (!userMovieRepository.existsByUsuarioAndMovie(user2.getId(), movie2.getId())) {
            UserMovie userMovie2 = new UserMovie();
            userMovie2.setUsuario(user2);
            userMovie2.setMovie(movie2);
            userMovie2.setStatus(MovieStatus.WATCHED);
            userMovie2.setReview("Classic film, must watch!");
            userMovie2.setRating(new BigDecimal("9.5"));
            userMovieRepository.save(userMovie2);
        }
    }
}
