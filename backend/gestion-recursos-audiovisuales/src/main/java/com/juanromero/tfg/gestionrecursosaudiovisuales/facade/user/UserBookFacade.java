package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user;

import java.math.BigDecimal;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserBookRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserBookResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.BookStatus;

public interface UserBookFacade {
    
    UserBookResponse addUserBook(UserBookRequest userBookRequest);

    UserBookResponse deleteUserBook(UserBookRequest userBookRequest);

    UserBookResponse findAllUserBooks(String usuarioNombre);

    UserBookResponse findUserBooksByStatus(String usuarioNombre, BookStatus status);

    UserBookResponse moveUserBookToStatus(String usuarioNombre, String bookTitle, BookStatus status);
    
    UserBookResponse updateUserBookReview(String usuarioNombre, String bookTitle, String review);

    UserBookResponse updateUserBookRating(String usuarioNombre, String bookTitle, BigDecimal rating);
}