package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserBookRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserBookResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.BookStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserBook;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user.UserBookFacade;
import com.juanromero.tfg.gestionrecursosaudiovisuales.mapper.user.UserBookMapper;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserBookRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.UserBookService;

@Service
public class UserBookFacadeImpl implements UserBookFacade {

    @Autowired
    private UserBookService userBookService;

    @Autowired
    private UserBookMapper userBookMapper;

    @Autowired
    private UserBookRepository userBookRepository;

    @Override
    public UserBookResponse addUserBook(UserBookRequest userBookRequest) {
        UserBookResponse response = new UserBookResponse();
        String descripcionPeticion = "";

        UserBook userBook = userBookMapper.dtoToEntity(userBookRequest);
        descripcionPeticion = userBookService.addUserBook(userBook);
        response.setDescripcionPeticion(descripcionPeticion);
        userBookRequest.setId(userBook.getId());

        if (descripcionPeticion.contains("añadido")) {
            List<UserBookRequest> userBooks = new ArrayList<>();
            response.setListaUserBooks(userBooks);
            response.getListaUserBooks().add(userBookRequest);
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }

    @Override
    public UserBookResponse deleteUserBook(UserBookRequest userBookRequest) {
        UserBookResponse response = new UserBookResponse();
        String descripcionPeticion = "";

        descripcionPeticion = userBookService.deleteUserBook(userBookRequest.getUserId(), userBookRequest.getBookId());
        response.setDescripcionPeticion(descripcionPeticion);

        if (descripcionPeticion.contains("eliminado")) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }

    @Override
    public UserBookResponse findAllUserBooks(Integer usuarioId) {
        UserBookResponse response = new UserBookResponse();
        List<UserBookRequest> userBooksDTO = new ArrayList<>();
        List<UserBook> userBooks = userBookService.findAllUserBooks(usuarioId);
        for (UserBook ub: userBooks) {
            userBooksDTO.add(userBookMapper.entityToDto(ub));
        }
        response.setListaUserBooks(userBooksDTO);

        if (!userBooks.isEmpty()) {
            response.setEstadoPeticion("OK");
            response.setDescripcionPeticion("Libros encontrados correctamente.");
        } else {
            response.setEstadoPeticion("KO");
            response.setDescripcionPeticion("No se encontraron libros para el usuario.");
        }

        return response;
    }

    @Override
    public UserBookResponse findUserBooksByStatus(Integer usuarioId, BookStatus status) {
        UserBookResponse response = new UserBookResponse();

        List<UserBook> userBooks = userBookService.findUserBooksByStatus(usuarioId, status);
        List<UserBookRequest> userBooksDTO = new ArrayList<>();
        for (UserBook ub: userBooks) {
            userBooksDTO.add(userBookMapper.entityToDto(ub));
        }
        response.setListaUserBooks(userBooksDTO);

        if (!userBooks.isEmpty()) {
            response.setEstadoPeticion("OK");
            response.setDescripcionPeticion("Libros encontrados correctamente.");
        } else {
            response.setEstadoPeticion("KO");
            response.setDescripcionPeticion("No se encontraron libros para el usuario con el estado especificado.");
        }

        return response;
    }

    @Override
    public UserBookResponse moveUserBookToStatus(Integer usuarioId, Integer bookId, BookStatus status) {
        UserBookResponse response = new UserBookResponse();
        Optional<UserBook> userBookOpt = userBookRepository.findByUsuarioIdAndBookId(usuarioId, bookId);
        
        if (userBookOpt.isPresent()) {
            UserBook userBook = userBookOpt.get();

            // Guardar la fecha de inicio si se cambia a READING
            if (status == BookStatus.READING) {
                userBook.setDateStarted(LocalDate.now());
            }

            // Guardar la fecha de lectura si se cambia a READED
            if (status == BookStatus.READED) {
                userBook.setDateRead(LocalDate.now());
            }

            // Actualizar el estado y guardar el UserBook
            userBook.setStatus(status);
            userBookRepository.save(userBook);

            // Construir UserBookRequest desde UserBook modificado
            UserBookRequest userBookRequest = userBookMapper.entityToDto(userBook);

            List<UserBookRequest> userBooks = new ArrayList<>();
            userBooks.add(userBookRequest);
            response.setListaUserBooks(userBooks);

            response.setEstadoPeticion("OK");
            response.setDescripcionPeticion("Libro movido a la lista de " + userBook.getStatus() + ".");
        } else {
            response.setDescripcionPeticion("El libro no está en la lista.");
            response.setEstadoPeticion("KO");
        }

        return response;
    }

    @Override
    public UserBookResponse updateUserBookReview(Integer userId, Integer bookId, String review) {
        UserBookResponse response = new UserBookResponse();
        String descripcionPeticion = userBookService.updateUserBookReview(userId, bookId, review);
        response.setDescripcionPeticion(descripcionPeticion);

        if (descripcionPeticion.contains("actualizada")) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }

    @Override
    public UserBookResponse updateUserBookRating(Integer userId, Integer bookId, BigDecimal rating) {
        UserBookResponse response = new UserBookResponse();
        String descripcionPeticion = userBookService.updateUserBookRating(userId, bookId, rating);
        response.setDescripcionPeticion(descripcionPeticion);

        if (descripcionPeticion.contains("actualizada")) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }
}
