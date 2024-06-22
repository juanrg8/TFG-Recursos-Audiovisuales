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

import com.juanromero.tfg.gestionrecursosaudiovisuales.controller.user.UserAlbumController;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserAlbumRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserAlbumResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.AlbumStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user.UserAlbumFacade;

public class UserAlbumControllerTest {


	    @InjectMocks
	    private UserAlbumController userAlbumController;

	    @Mock
	    private UserAlbumFacade userAlbumFacade;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    void testAddUserAlbum() {
	        UserAlbumRequest request = new UserAlbumRequest();
	        request.setSpotifyId("12345");
	        request.setUsuarioNombre("Test User");
	        request.setAlbumId(1);
	        request.setStatus(AlbumStatus.PENDING);
	        request.setReview("Great album!");
	        request.setRating(new BigDecimal("4.5"));
	        request.setFechaCambioEstado(LocalDate.now());

	        UserAlbumResponse expectedResponse = new UserAlbumResponse("success", "User album added successfully", Collections.singletonList(request));

	        when(userAlbumFacade.addUserAlbum(any(UserAlbumRequest.class))).thenReturn(expectedResponse);

	        UserAlbumResponse response = userAlbumController.add(request);

	        assertEquals(expectedResponse, response);
	        verify(userAlbumFacade, times(1)).addUserAlbum(any(UserAlbumRequest.class));
	    }
	    
	    @Test
	    void testDeleteUserAlbum() {
	        UserAlbumRequest request = new UserAlbumRequest();
	        request.setSpotifyId("12345");
	        request.setUsuarioNombre("Test User");
	        request.setAlbumId(1);

	        UserAlbumResponse expectedResponse = new UserAlbumResponse("success", "User album deleted successfully", Collections.singletonList(request));

	        when(userAlbumFacade.deleteUserAlbum(any(UserAlbumRequest.class))).thenReturn(expectedResponse);

	        UserAlbumResponse response = userAlbumController.delete(request);

	        assertEquals(expectedResponse, response);
	        verify(userAlbumFacade, times(1)).deleteUserAlbum(any(UserAlbumRequest.class));
	    }
	    
	    @Test
	    void testFindAllUserAlbums() {
	        UserAlbumRequest request = new UserAlbumRequest();
	        request.setSpotifyId("12345");
	        request.setUsuarioNombre("Test User");
	        request.setAlbumId(1);

	        List<UserAlbumRequest> userAlbumList = Collections.singletonList(request);
	        UserAlbumResponse expectedResponse = new UserAlbumResponse("success", "User albums found successfully", userAlbumList);

	        when(userAlbumFacade.findAllUserAlbums("Test User")).thenReturn(expectedResponse);

	        List<UserAlbumRequest> response = userAlbumController.findAllUserAlbums("Test User");

	        assertEquals(userAlbumList.size(), response.size());
	        assertEquals(userAlbumList.get(0), response.get(0));
	        verify(userAlbumFacade, times(1)).findAllUserAlbums("Test User");
	    }
	    
	    @Test
	    void testFindUserAlbumsByStatus() {
	        UserAlbumRequest request = new UserAlbumRequest();
	        request.setSpotifyId("12345");
	        request.setUsuarioNombre("Test User");
	        request.setAlbumId(1);
	        request.setStatus(AlbumStatus.PENDING);

	        List<UserAlbumRequest> userAlbumList = Collections.singletonList(request);
	        UserAlbumResponse expectedResponse = new UserAlbumResponse("success", "User albums by status found successfully", userAlbumList);

	        when(userAlbumFacade.findUserAlbumsByStatus("Test User", AlbumStatus.PENDING)).thenReturn(expectedResponse);

	        List<UserAlbumRequest> response = userAlbumController.findUserAlbumsByStatus("Test User", AlbumStatus.PENDING);

	        assertEquals(userAlbumList.size(), response.size());
	        assertEquals(userAlbumList.get(0), response.get(0));
	        verify(userAlbumFacade, times(1)).findUserAlbumsByStatus("Test User", AlbumStatus.PENDING);
	    }
	    
	    @Test
	    void testMoveUserAlbumToStatus() {
	        UserAlbumRequest request = new UserAlbumRequest();
	        request.setSpotifyId("12345");
	        request.setUsuarioNombre("Test User");
	        request.setTituloAlbum("Test Album");
	        request.setStatus(AlbumStatus.CONSUMED);

	        UserAlbumResponse expectedResponse = new UserAlbumResponse("success", "User album moved to status successfully", Collections.singletonList(request));

	        when(userAlbumFacade.moveUserAlbumToStatus("Test User", "Test Album", AlbumStatus.CONSUMED)).thenReturn(expectedResponse);

	        UserAlbumResponse response = userAlbumController.moveUserAlbumToStatus(request);

	        assertEquals(expectedResponse, response);
	        verify(userAlbumFacade, times(1)).moveUserAlbumToStatus("Test User", "Test Album", AlbumStatus.CONSUMED);
	    }
	    
	    @Test
	    void testUpdateUserAlbumReview() {
	        UserAlbumRequest request = new UserAlbumRequest();
	        request.setSpotifyId("12345");
	        request.setUsuarioNombre("Test User");
	        request.setTituloAlbum("Test Album");
	        request.setReview("Updated review");

	        UserAlbumResponse expectedResponse = new UserAlbumResponse("success", "User album review updated successfully", Collections.singletonList(request));

	        when(userAlbumFacade.updateUserAlbumReview("Test User", "Test Album", "Updated review")).thenReturn(expectedResponse);

	        UserAlbumResponse response = userAlbumController.updateUserAlbumReview(request);

	        assertEquals(expectedResponse, response);
	        verify(userAlbumFacade, times(1)).updateUserAlbumReview("Test User", "Test Album", "Updated review");
	    }
	    
	    @Test
	    void testUpdateUserAlbumRating() {
	        UserAlbumRequest request = new UserAlbumRequest();
	        request.setSpotifyId("12345");
	        request.setUsuarioNombre("Test User");
	        request.setTituloAlbum("Test Album");
	        request.setRating(new BigDecimal("4.8"));

	        UserAlbumResponse expectedResponse = new UserAlbumResponse("success", "User album rating updated successfully", Collections.singletonList(request));

	        when(userAlbumFacade.updateUserAlbumRating("Test User", "Test Album", new BigDecimal("4.8"))).thenReturn(expectedResponse);

	        UserAlbumResponse response = userAlbumController.updateUserAlbumRating(request);

	        assertEquals(expectedResponse, response);
	        verify(userAlbumFacade, times(1)).updateUserAlbumRating("Test User", "Test Album", new BigDecimal("4.8"));
	    }
	    
	    @Test
	    void testAddUserAlbumInvalidRequest() {
	        UserAlbumRequest request = new UserAlbumRequest();
	        UserAlbumResponse expectedResponse = new UserAlbumResponse("error", "Invalid request", null);

	        when(userAlbumFacade.addUserAlbum(any(UserAlbumRequest.class))).thenReturn(expectedResponse);

	        UserAlbumResponse response = userAlbumController.add(request);

	        assertEquals(expectedResponse, response);
	        verify(userAlbumFacade, times(1)).addUserAlbum(any(UserAlbumRequest.class));
	    }

	    @Test
	    void testDeleteUserAlbumNotFound() {
	        UserAlbumRequest request = createUserAlbumRequest();
	        UserAlbumResponse expectedResponse = new UserAlbumResponse("error", "User album not found", null);

	        when(userAlbumFacade.deleteUserAlbum(any(UserAlbumRequest.class))).thenReturn(expectedResponse);

	        UserAlbumResponse response = userAlbumController.delete(request);

	        assertEquals(expectedResponse, response);
	        verify(userAlbumFacade, times(1)).deleteUserAlbum(any(UserAlbumRequest.class));
	    }

	    private UserAlbumRequest createUserAlbumRequest() {
	        UserAlbumRequest request = new UserAlbumRequest();
	        request.setSpotifyId("12345");
	        request.setUsuarioNombre("Test User");
	        request.setAlbumId(1);
	        request.setStatus(AlbumStatus.PENDING);
	        request.setReview("Great album!");
	        request.setRating(new BigDecimal("4.5"));
	        request.setFechaCambioEstado(LocalDate.now());
	        return request;
	    }

}
