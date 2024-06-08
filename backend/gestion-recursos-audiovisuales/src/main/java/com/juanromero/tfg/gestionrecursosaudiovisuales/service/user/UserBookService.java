package com.juanromero.tfg.gestionrecursosaudiovisuales.service.user;

import java.math.BigDecimal;
import java.util.List;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.BookStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserBook;

public interface UserBookService {
    
    String addUserBook(UserBook userBook);

    String deleteUserBook(Integer userId, Integer bookId);

    List<UserBook> findAllUserBooks(Integer userId);

    List<UserBook> findUserBooksByStatus(Integer userId, BookStatus status);

    String moveUserBookToStatus(Integer userId, Integer bookId, BookStatus status);

    String updateUserBookReview(Integer userId, Integer bookId, String review);

    String updateUserBookRating(Integer userId, Integer bookId, BigDecimal rating);

    UserBook findUserBookById(Integer id);
}
