package com.juanromero.tfg.gestionrecursosaudiovisuales.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.juanromero.tfg.gestionrecursosaudiovisuales.controller.user.UserController;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user.UserFacade;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserFacade userFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        UserRequest request = new UserRequest();
        request.setUsername("testuser");
        request.setPassword("password");

        UserResponse expectedResponse = new UserResponse("OK", "Usuario añadido correctamente", new ArrayList<>(), request);

        when(userFacade.addUser(any(UserRequest.class))).thenReturn(expectedResponse);

        UserResponse response = userController.add(request);

        verify(userFacade).addUser(any(UserRequest.class));

        assertEquals("OK", response.getEstadoPeticion());
        assertEquals("Usuario añadido correctamente", response.getDescripcionPeticion());
        assertEquals(request, response.getUsuario());
        assertEquals(Collections.emptyList(), response.getListaUsuarios());
    }
    
    @Test
    void testDeleteUser() {
        UserRequest request = new UserRequest();
        request.setUsername("testuser");
        request.setPassword("password");

        UserResponse expectedResponse = new UserResponse("OK", "Usuario eliminado correctamente", new ArrayList<>(), request);

        when(userFacade.deleteUser(any(UserRequest.class))).thenReturn(expectedResponse);

        UserResponse response = userController.delete(request);

        verify(userFacade).deleteUser(any(UserRequest.class));

        assertEquals("OK", response.getEstadoPeticion());
        assertEquals("Usuario eliminado correctamente", response.getDescripcionPeticion());
        assertEquals(request, response.getUsuario());
        assertEquals(Collections.emptyList(), response.getListaUsuarios());
    }
    
    @Test
    void testUpdateUser() {
        UserRequest request = new UserRequest();
        request.setUsername("testuser");
        request.setPassword("password");

        UserResponse expectedResponse = new UserResponse("OK", "Usuario actualizado correctamente", new ArrayList<>(), request);

        when(userFacade.updateUser(any(UserRequest.class))).thenReturn(expectedResponse);

        UserResponse response = userController.update(request);

        verify(userFacade).updateUser(any(UserRequest.class));

        assertEquals("OK", response.getEstadoPeticion());
        assertEquals("Usuario actualizado correctamente", response.getDescripcionPeticion());
        assertEquals(request, response.getUsuario());
        assertEquals(Collections.emptyList(), response.getListaUsuarios());
    }
    
    @Test
    void testFindAllUsers() {
        UserResponse expectedResponse = new UserResponse("OK", "Usuarios encontrados", new ArrayList<>(), null);

        when(userFacade.findUser()).thenReturn(expectedResponse);

        UserResponse response = userController.findAllUsers();

        verify(userFacade).findUser();

        assertEquals("OK", response.getEstadoPeticion());
        assertEquals("Usuarios encontrados", response.getDescripcionPeticion());
        assertEquals(Collections.emptyList(), response.getListaUsuarios());
        assertEquals(null, response.getUsuario());
    }

    @Test
    void testFindUsers() {
        String username = "testuser";
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(username);
        UserResponse expectedResponse = new UserResponse("OK", "Usuario encontrado", new ArrayList<>(), userRequest);

        when(userFacade.findUserByUsername(username)).thenReturn(expectedResponse);

        UserResponse response = userController.findUsers(username);

        verify(userFacade).findUserByUsername(username);

        assertEquals("OK", response.getEstadoPeticion());
        assertEquals("Usuario encontrado", response.getDescripcionPeticion());
        assertEquals(Collections.emptyList(), response.getListaUsuarios());
        assertEquals(userRequest, response.getUsuario());
    }

    
}


