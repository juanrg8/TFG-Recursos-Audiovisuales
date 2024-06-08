package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user;

import java.math.BigDecimal;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserBookRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserBookResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.BookStatus;

public interface UserBookFacade {
    
    UserBookResponse addUserBook(UserBookRequest userBookRequest);

    UserBookResponse deleteUserBook(UserBookRequest userBookRequest);

    UserBookResponse findAllUserBooks(Integer usuarioId);

    UserBookResponse findUserBooksByStatus(Integer usuarioId, BookStatus status);

    UserBookResponse moveUserBookToStatus(Integer usuarioId, Integer bookId, BookStatus status);
    
    UserBookResponse updateUserBookReview(Integer userId, Integer bookId, String review);

    UserBookResponse updateUserBookRating(Integer userId, Integer bookId, BigDecimal rating);
}
