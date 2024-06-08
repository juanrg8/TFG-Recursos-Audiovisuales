package com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.BookStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserBook;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserBookRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.UserBookService;

@Service
public class UserBookServiceImpl implements UserBookService {

    @Autowired
    private UserBookRepository userBookRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public String addUserBook(UserBook userBook) {
        // Verificar si el libro ya está en la lista del usuario
        Optional<UserBook> existingBook = userBookRepository.findByUsuarioIdAndBookId(userBook.getUsuario().getId(), userBook.getBook().getId());
        if (existingBook.isPresent()) {
            return "El libro ya está en la lista del usuario.";
        }

        // Guardar el UserBook
        userBookRepository.save(userBook);

        // Añadir el UserBook a la lista del usuario
        User user = userBook.getUsuario();
        user.getUserBooks().add(userBook);
        userRepository.save(user);

        return "Libro añadido a la lista del usuario.";
    }

    @Transactional
    @Override
    public String deleteUserBook(Integer userId, Integer bookId) {
        // Verificar si el libro está en la lista del usuario
        Optional<UserBook> existingBook = userBookRepository.findByUsuarioIdAndBookId(userId, bookId);
        if (!existingBook.isPresent()) {
            return "El libro no está en la lista del usuario.";
        }

        // Eliminar el UserBook
        UserBook userBook = existingBook.get();
        userBookRepository.delete(userBook);

        // Eliminar el UserBook de la lista del usuario
        User user = userBook.getUsuario();
        user.getUserBooks().remove(userBook);
        userRepository.save(user);

        return "Libro eliminado de la lista del usuario.";
    }

    @Override
    public List<UserBook> findAllUserBooks(Integer userId) {
        return userBookRepository.findByUsuarioId(userId);
    }

    @Override
    public List<UserBook> findUserBooksByStatus(Integer userId, BookStatus status) {
        return userBookRepository.findByUsuarioIdAndStatus(userId, status);
    }

    @Transactional
    @Override
    public String moveUserBookToStatus(Integer userId, Integer bookId, BookStatus status) {
        Optional<UserBook> userBookOpt = userBookRepository.findByUsuarioIdAndBookId(userId, bookId);
        if (userBookOpt.isPresent()) {
            UserBook userBook = userBookOpt.get();
            userBook.setStatus(status);

            // Establecer fechas según el estado
            if (status == BookStatus.READING) {
                userBook.setDateStarted(LocalDate.now());
            } else if (status == BookStatus.READED) {
                userBook.setDateRead(LocalDate.now());
            }

            userBookRepository.save(userBook);
            return "Libro movido a " + status + ".";
        }
        return "El libro no está en la lista.";
    }

    @Transactional
    @Override
    public String updateUserBookReview(Integer userId, Integer bookId, String review) {
        Optional<UserBook> userBookOpt = userBookRepository.findByUsuarioIdAndBookId(userId, bookId);
        if (userBookOpt.isPresent()) {
            UserBook userBook = userBookOpt.get();
            userBook.setReview(review);
            userBookRepository.save(userBook);
            return "Review del libro actualizada.";
        }
        return "El libro no está en la lista.";
    }

    @Transactional
    @Override
    public String updateUserBookRating(Integer userId, Integer bookId, BigDecimal rating) {
        Optional<UserBook> userBookOpt = userBookRepository.findByUsuarioIdAndBookId(userId, bookId);
        if (userBookOpt.isPresent()) {
            UserBook userBook = userBookOpt.get();
            userBook.setRating(rating);
            userBookRepository.save(userBook);
            return "Rating del libro actualizado.";
        }
        return "El libro no está en la lista.";
    }

    @Override
    public UserBook findUserBookById(Integer id) {
        return userBookRepository.findById(id).orElse(null);
    }
}
