package com.juanromero.tfg.gestionrecursosaudiovisuales.user;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.Rol;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setNombre("Test User");
        user.setPassword("password");
        user.setEmail("testuser@example.com");
        user.setRol(Rol.USER);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        String result = userService.addUser(user);

        verify(userRepository, times(1)).findByUsername("testuser");
        verify(userRepository, times(1)).save(any(User.class));

        assertEquals("Usuario guardado con exito", result);
    }

    @Test
    void testAddUser_DuplicateUser() {
        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setNombre("Test User");
        user.setPassword("password");
        user.setEmail("testuser@example.com");
        user.setRol(Rol.USER);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        String result = userService.addUser(user);

        verify(userRepository, times(1)).findByUsername("testuser");
        verify(userRepository, never()).save(any(User.class));

        assertEquals("No ha sido posible guardar el usuario, usuario duplicado", result);
    }

    @Test
    void testDeleteUser() {
        User userToDelete = new User();
        userToDelete.setUsername("testuser");
        userToDelete.setRol(Rol.USER); 

        Optional<User> optionalUserBeforeDelete = Optional.of(userToDelete);
        when(userRepository.findByUsername("testuser")).thenReturn(optionalUserBeforeDelete);

        userService.deleteUser(userToDelete);

        verify(userRepository, times(2)).findByUsername("testuser");

        verify(userRepository, times(1)).delete(any(User.class));

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        String resultAfterDelete = userService.deleteUser(userToDelete);

        verify(userRepository, times(3)).findByUsername("testuser");

        // El método siempre va a devolver que no se ha podido borrar porque al 
        //ser un test no accede realmente a base de datos, de modo que se llama 
        //2 veces a borrar esperando que en la segunda llamada no esté el usuario
        assertEquals("Error: El usuario no existe.", resultAfterDelete);
    }









    @Test
    void testDeleteUser_AdminUser() {
        User user = new User();
        user.setId(1);
        user.setUsername("adminuser");
        user.setNombre("Admin User");
        user.setPassword("password");
        user.setEmail("adminuser@example.com");
        user.setRol(Rol.ADMIN);

        when(userRepository.findByUsername("adminuser")).thenReturn(Optional.of(user));

        String result = userService.deleteUser(user);

        verify(userRepository, times(1)).findByUsername("adminuser");
        verify(userRepository, never()).delete(any(User.class));

        assertEquals("No se puede eliminar a un administrador", result);
    }

    @Test
    void testDeleteUser_UserNotFound() {
        User user = new User();
        user.setId(1);
        user.setUsername("unknownuser");
        user.setNombre("Unknown User");
        user.setPassword("password");
        user.setEmail("unknownuser@example.com");
        user.setRol(Rol.USER);

        when(userRepository.findByUsername("unknownuser")).thenReturn(Optional.empty());

        String result = userService.deleteUser(user);

        verify(userRepository, times(1)).findByUsername("unknownuser");
        verify(userRepository, never()).delete(any(User.class));

        assertEquals("Error: El usuario no existe.", result);
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setNombre("Test User");
        user.setPassword("password");
        user.setEmail("testuser@example.com");
        user.setRol(Rol.USER);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        String result = userService.updateUser(user);

        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(any(User.class));

        assertEquals("El usuario se ha actualizado correctamente.", result);
    }

    @Test
    void testUpdateUser_UserNotFound() {
        User user = new User();
        user.setId(1);
        user.setUsername("unknownuser");
        user.setNombre("Unknown User");
        user.setPassword("password");
        user.setEmail("unknownuser@example.com");
        user.setRol(Rol.USER);

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        String result = userService.updateUser(user);

        verify(userRepository, times(1)).findById(1);
        verify(userRepository, never()).save(any(User.class));

        assertEquals("Error: El usuario no existe.", result);
    }

    @Test
    void testFindAllUsers() {
        // Configurar datos de prueba
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("user1");
        user1.setNombre("Nombre1");
        user1.setPassword("password1");
        user1.setEmail("user1@example.com");
        user1.setRol(Rol.USER);

        User user2 = new User();
        user2.setId(2);
        user2.setUsername("user2");
        user2.setNombre("Nombre2");
        user2.setPassword("password2");
        user2.setEmail("user2@example.com");
        user2.setRol(Rol.USER);

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        // Configurar el mock del repositorio
        when(userRepository.findAll()).thenReturn(userList);

        // Llamar al método del servicio que debe interactuar con el repositorio
        List<User> result = userService.findAllUsers();

        // Verificar que se llamó al método findAll del repositorio exactamente una vez
        verify(userRepository, times(1)).findAll();

        // Verificar el resultado esperado de la prueba
        assertEquals(userList.size(), result.size());
        assertEquals(userList.get(0).getUsername(), result.get(0).getUsername());
        assertEquals(userList.get(1).getUsername(), result.get(1).getUsername());
    }


    @Test
    void testFindAllUsers_NoUsersFound() {
        when(userRepository.findAll()).thenReturn(null);

        List<User> result = userService.findAllUsers();

        verify(userRepository, times(1)).findAll();

        assertEquals(null, result);
    }

    @Test
    void testFindUserByUsername() {
        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setNombre("Test User");
        user.setPassword("password");
        user.setEmail("testuser@example.com");
        user.setRol(Rol.USER);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        User result = userService.findUserByUsername("testuser");

        verify(userRepository, times(1)).findByUsername("testuser");

        assertEquals("testuser", result.getUsername());
    }

    @Test
    void testFindUserByUsername_UserNotFound() {
        when(userRepository.findByUsername("unknownuser")).thenReturn(Optional.empty());

        User result = userService.findUserByUsername("unknownuser");

        verify(userRepository, times(1)).findByUsername("unknownuser");

        assertEquals(null, result);
    }
}
