package com.juanromero.tfg.gestionrecursosaudiovisuales.initializer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.book.Book;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.BookStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserBook;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.book.BookRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserBookRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;

import org.springframework.boot.ApplicationArguments;

@Component
public class UserBookInitializer implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserBookRepository userBookRepository;

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

        // Verificar y crear libros si no existen
        Book book1 = bookRepository.findByTitle("1984");
        if (book1 == null) {
            book1 = new Book();
            book1.setTitle("1984");
            book1.setPages(328);
            book1.setAuthor("George Orwell");
            book1.setPublishDate(LocalDate.parse("1949-06-08"));
            book1.setGenre("Fiction");
            book1 = bookRepository.save(book1);
        }

        Book book2 = bookRepository.findByTitle("To Kill a Mockingbird");
        if (book2 == null) {
            book2 = new Book();
            book2.setTitle("To Kill a Mockingbird");
            book2.setPages(281);
            book2.setAuthor("Harper Lee");
            book2.setPublishDate(LocalDate.parse("1960-07-11"));
            book2.setGenre("Fiction");
            book2 = bookRepository.save(book2);
        }

        // Verificar y crear relaciones UserBook si no existen
        if (!userBookRepository.existsByUsuarioAndBook(user1.getId(), book1.getId())) {
            UserBook userBook1 = new UserBook();
            userBook1.setUsuario(user1);
            userBook1.setBook(book1);
            userBook1.setStatus(BookStatus.PENDING);
            userBook1.setReview("A must-read classic.");
            userBook1.setRating(new BigDecimal("9.0"));
            userBookRepository.save(userBook1);
        }

        if (!userBookRepository.existsByUsuarioAndBook(user2.getId(), book1.getId())) {
            UserBook userBook2 = new UserBook();
            userBook2.setUsuario(user2);
            userBook2.setBook(book1);
            userBook2.setStatus(BookStatus.READING);
            userBook2.setReview("Fascinating read so far.");
            userBook2.setRating(new BigDecimal("8.5"));
            userBook2.setDateStarted(LocalDate.now());
            userBookRepository.save(userBook2);
        }

        if (!userBookRepository.existsByUsuarioAndBook(user1.getId(), book2.getId())) {
            UserBook userBook3 = new UserBook();
            userBook3.setUsuario(user1);
            userBook3.setBook(book2);
            userBook3.setStatus(BookStatus.READED);
            userBook3.setReview("A classic that everyone should read.");
            userBook3.setRating(new BigDecimal("10.0"));
            userBook3.setDateRead(LocalDate.now());
            userBookRepository.save(userBook3);
        }
    }
}
