package com.juanromero.tfg.gestionrecursosaudiovisuales.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.juanromero.tfg.gestionrecursosaudiovisuales.controller.user.UserBookController;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserBookRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserBookResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.BookStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user.UserBookFacade;

public class UserBookControllerTest {

    @InjectMocks
    private UserBookController userBookController;

    @Mock
    private UserBookFacade userBookFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    private UserBookRequest createUserBookRequest() {
        UserBookRequest request = new UserBookRequest();
        request.setLibroId("12345");
        request.setUsuarioNombre("Test User");
        request.setBookId(1);
        request.setStatus(BookStatus.PENDING);
        request.setReview("Great book!");
        request.setRating(new BigDecimal("4.5"));
        request.setDateStarted(LocalDate.now());
        request.setDateRead(LocalDate.now());
        return request;
    }

    @Test
    void testAddUserBook() {
        UserBookRequest request = createUserBookRequest();
        UserBookResponse expectedResponse = new UserBookResponse("success", "User book added successfully", Collections.singletonList(request));

        when(userBookFacade.addUserBook(any(UserBookRequest.class))).thenReturn(expectedResponse);

        UserBookResponse response = userBookController.add(request);

        assertEquals(expectedResponse, response);
        verify(userBookFacade, times(1)).addUserBook(any(UserBookRequest.class));
    }
    
    
    @Test
    void testDeleteUserBook() {
        UserBookRequest request = createUserBookRequest();
        UserBookResponse expectedResponse = new UserBookResponse("success", "User book deleted successfully", Collections.singletonList(request));

        when(userBookFacade.deleteUserBook(any(UserBookRequest.class))).thenReturn(expectedResponse);

        UserBookResponse response = userBookController.delete(request);

        assertEquals(expectedResponse, response);
        verify(userBookFacade, times(1)).deleteUserBook(any(UserBookRequest.class));
    }

    @Test
    void testFindAllUserBooks() {
        UserBookRequest request = createUserBookRequest();
        List<UserBookRequest> userBookList = Collections.singletonList(request);
        UserBookResponse expectedResponse = new UserBookResponse("success", "User books found successfully", userBookList);

        when(userBookFacade.findAllUserBooks("Test User")).thenReturn(expectedResponse);

        List<UserBookRequest> response = userBookController.findAllUserBooks("Test User");

        assertEquals(userBookList.size(), response.size());
        assertEquals(userBookList.get(0), response.get(0));
        verify(userBookFacade, times(1)).findAllUserBooks("Test User");
    }

    @Test
    void testFindUserBooksByStatus() {
        UserBookRequest request = createUserBookRequest();
        List<UserBookRequest> userBookList = Collections.singletonList(request);
        UserBookResponse expectedResponse = new UserBookResponse("success", "User books by status found successfully", userBookList);

        when(userBookFacade.findUserBooksByStatus("Test User", BookStatus.READING)).thenReturn(expectedResponse);

        List<UserBookRequest> response = userBookController.findUserBooksByStatus("Test User", BookStatus.READING);

        assertEquals(userBookList.size(), response.size());
        assertEquals(userBookList.get(0), response.get(0));
        verify(userBookFacade, times(1)).findUserBooksByStatus("Test User", BookStatus.READING);
    }

    @Test
    void testMoveUserBookToStatus() {
        UserBookRequest request = new UserBookRequest();
        request.setLibroId("12345");
        request.setUsuarioNombre("Usuario de Prueba");
        request.setTituloLibro("Libro de Prueba");
        request.setStatus(BookStatus.READING);

        UserBookResponse expectedResponse = new UserBookResponse("success", "Libro del usuario movido exitosamente al estado de lectura", Collections.singletonList(request));

        when(userBookFacade.moveUserBookToStatus("Usuario de Prueba", "Libro de Prueba", BookStatus.READING)).thenReturn(expectedResponse);

        UserBookResponse response = userBookController.moveUserBookToStatus(request);

        assertEquals(expectedResponse, response);
        verify(userBookFacade, times(1)).moveUserBookToStatus("Usuario de Prueba", "Libro de Prueba", BookStatus.READING);
    }

    @Test
    void testUpdateUserBookReview() {
        UserBookRequest request = new UserBookRequest();
        request.setLibroId("12345");
        request.setUsuarioNombre("Usuario de Prueba");
        request.setTituloLibro("Libro de Prueba");
        request.setReview("Reseña actualizada");

        UserBookResponse expectedResponse = new UserBookResponse("success", "Reseña del libro actualizada correctamente", Collections.singletonList(request));

        when(userBookFacade.updateUserBookReview("Usuario de Prueba", "Libro de Prueba", "Reseña actualizada")).thenReturn(expectedResponse);

        UserBookResponse response = userBookController.updateUserBookReview(request);

        assertEquals(expectedResponse, response);
        verify(userBookFacade, times(1)).updateUserBookReview("Usuario de Prueba", "Libro de Prueba", "Reseña actualizada");
    }

    @Test
    void testUpdateUserBookRating() {
        UserBookRequest request = new UserBookRequest();
        request.setLibroId("12345");
        request.setUsuarioNombre("Test User");
        request.setTituloLibro("Test Book");
        request.setRating(new BigDecimal("4.8"));

        UserBookResponse expectedResponse = new UserBookResponse("success", "User book rating updated successfully", Collections.singletonList(request));

        when(userBookFacade.updateUserBookRating("Test User", "Test Book", new BigDecimal("4.8"))).thenReturn(expectedResponse);

        UserBookResponse response = userBookController.updateUserBookRating(request);

        assertEquals(expectedResponse, response);
        verify(userBookFacade, times(1)).updateUserBookRating("Test User", "Test Book", new BigDecimal("4.8"));
    }

    @Test
    void testAddUserBookInvalidRequest() {
        UserBookRequest request = new UserBookRequest();
        UserBookResponse expectedResponse = new UserBookResponse("error", "Invalid request", null);

        when(userBookFacade.addUserBook(any(UserBookRequest.class))).thenReturn(expectedResponse);

        UserBookResponse response = userBookController.add(request);

        assertEquals(expectedResponse, response);
        verify(userBookFacade, times(1)).addUserBook(any(UserBookRequest.class));
    }

    @Test
    void testDeleteUserBookNotFound() {
        UserBookRequest request = createUserBookRequest();
        UserBookResponse expectedResponse = new UserBookResponse("error", "User book not found", null);

        when(userBookFacade.deleteUserBook(any(UserBookRequest.class))).thenReturn(expectedResponse);

        UserBookResponse response = userBookController.delete(request);

        assertEquals(expectedResponse, response);
        verify(userBookFacade, times(1)).deleteUserBook(any(UserBookRequest.class));
    }
	
}
