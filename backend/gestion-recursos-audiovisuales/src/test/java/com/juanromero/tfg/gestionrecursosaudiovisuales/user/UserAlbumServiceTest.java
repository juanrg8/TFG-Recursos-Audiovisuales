package com.juanromero.tfg.gestionrecursosaudiovisuales.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album.Album;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.AlbumStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserAlbum;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserAlbumRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.impl.UserAlbumServiceImpl;

public class UserAlbumServiceTest {

    @InjectMocks
    private UserAlbumServiceImpl userAlbumService;

    @Mock
    private UserAlbumRepository userAlbumRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private User createUser() {
        User user = new User();
        user.setId(1);
        user.setNombre("Test User");
        List<UserAlbum> userAlbums = new ArrayList<>();
        user.setUserAlbums(userAlbums);
        return user;
    }

    private UserAlbum createUserAlbum(User user) {
        UserAlbum userAlbum = new UserAlbum();
        userAlbum.setUsuario(user);
        Album album = new Album();
        album.setId(1);
        userAlbum.setAlbum(album);
        userAlbum.setStatus(AlbumStatus.PENDING);
        userAlbum.setReview("Great album!");
        userAlbum.setRating(new BigDecimal("4.5"));
        return userAlbum;
    }

    
    @Test
    void testAddUserAlbumSuccess() {
        User user = createUser();
        UserAlbum userAlbum = createUserAlbum(user);
        when(userAlbumRepository.findByUsuarioIdAndAlbumId(any(Integer.class), any(Integer.class)))
            .thenReturn(Optional.empty());

        String result = userAlbumService.addUserAlbum(userAlbum);

        assertEquals("Álbum añadido a la lista del usuario.", result);
        verify(userAlbumRepository, times(1)).save(any(UserAlbum.class));
        verify(userRepository, times(1)).save(any(User.class));
    }
    
    @Test
    void testAddUserAlbumAlreadyExists() {
        User user = createUser();
        UserAlbum userAlbum = createUserAlbum(user);
        when(userAlbumRepository.findByUsuarioIdAndAlbumId(any(Integer.class), any(Integer.class)))
            .thenReturn(Optional.of(userAlbum));

        String result = userAlbumService.addUserAlbum(userAlbum);

        assertEquals("El álbum ya está en la lista del usuario.", result);
        verify(userAlbumRepository, times(0)).save(any(UserAlbum.class));
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testDeleteUserAlbumSuccess() {
        User user = createUser();
        UserAlbum userAlbum = createUserAlbum(user);
        when(userAlbumRepository.findByUsuarioIdAndAlbumId(any(Integer.class), any(Integer.class)))
            .thenReturn(Optional.of(userAlbum));

        String result = userAlbumService.deleteUserAlbum(user.getId(), userAlbum.getAlbum().getId());

        assertEquals("Álbum eliminado de la lista del usuario.", result);
        verify(userAlbumRepository, times(1)).delete(any(UserAlbum.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUserAlbumNotFound() {
        when(userAlbumRepository.findByUsuarioIdAndAlbumId(any(Integer.class), any(Integer.class)))
            .thenReturn(Optional.empty());

        String result = userAlbumService.deleteUserAlbum(1, 1);

        assertEquals("El álbum no está en la lista del usuario.", result);
        verify(userAlbumRepository, times(0)).delete(any(UserAlbum.class));
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testFindAllUserAlbums() {
        User user = createUser();
        UserAlbum userAlbum = createUserAlbum(user);
        when(userAlbumRepository.findByUsuarioId(any(Integer.class)))
            .thenReturn(Collections.singletonList(userAlbum));

        List<UserAlbum> result = userAlbumService.findAllUserAlbums(user.getId());

        assertEquals(1, result.size());
        assertEquals(userAlbum, result.get(0));
        verify(userAlbumRepository, times(1)).findByUsuarioId(any(Integer.class));
    }

    @Test
    void testFindUserAlbumsByStatus() {
        User user = createUser();
        UserAlbum userAlbum = createUserAlbum(user);
        userAlbum.setStatus(AlbumStatus.PENDING);
        when(userAlbumRepository.findByUsuarioIdAndStatus(any(Integer.class), any(AlbumStatus.class)))
            .thenReturn(Collections.singletonList(userAlbum));

        List<UserAlbum> result = userAlbumService.findUserAlbumsByStatus(user.getId(), AlbumStatus.PENDING);

        assertEquals(1, result.size());
        assertEquals(userAlbum, result.get(0));
        verify(userAlbumRepository, times(1)).findByUsuarioIdAndStatus(any(Integer.class), any(AlbumStatus.class));
    }

    @Test
    void testMoveUserAlbumToStatusSuccess() {
        User user = createUser();
        UserAlbum userAlbum = createUserAlbum(user);
        when(userAlbumRepository.findByUsuarioIdAndAlbumId(any(Integer.class), any(Integer.class)))
            .thenReturn(Optional.of(userAlbum));

        String result = userAlbumService.moveUserAlbumToStatus(user.getId(), userAlbum.getAlbum().getId(), AlbumStatus.CONSUMED);

        assertEquals("Álbum movido a la lista de CONSUMED.", result);
        verify(userAlbumRepository, times(1)).save(any(UserAlbum.class));
    }

    @Test
    void testMoveUserAlbumToStatusNotFound() {
        when(userAlbumRepository.findByUsuarioIdAndAlbumId(any(Integer.class), any(Integer.class)))
            .thenReturn(Optional.empty());

        String result = userAlbumService.moveUserAlbumToStatus(1, 1, AlbumStatus.CONSUMED);

        assertEquals("El álbum no está en la lista.", result);
        verify(userAlbumRepository, times(0)).save(any(UserAlbum.class));
    }

    @Test
    void testUpdateUserAlbumReviewSuccess() {
        User user = createUser();
        UserAlbum userAlbum = createUserAlbum(user);
        when(userAlbumRepository.findByUsuarioIdAndAlbumId(any(Integer.class), any(Integer.class)))
            .thenReturn(Optional.of(userAlbum));

        String result = userAlbumService.updateUserAlbumReview(user.getId(), userAlbum.getAlbum().getId(), "Updated review");

        assertEquals("Revisión actualizada correctamente.", result);
        verify(userAlbumRepository, times(1)).save(any(UserAlbum.class));
    }

    @Test
    void testUpdateUserAlbumReviewNotFound() {
        when(userAlbumRepository.findByUsuarioIdAndAlbumId(any(Integer.class), any(Integer.class)))
            .thenReturn(Optional.empty());

        String result = userAlbumService.updateUserAlbumReview(1, 1, "Updated review");

        assertEquals("No se pudo encontrar el álbum en la lista del usuario.", result);
        verify(userAlbumRepository, times(0)).save(any(UserAlbum.class));
    }

    @Test
    void testUpdateUserAlbumRatingSuccess() {
        User user = createUser();
        UserAlbum userAlbum = createUserAlbum(user);
        when(userAlbumRepository.findByUsuarioIdAndAlbumId(any(Integer.class), any(Integer.class)))
            .thenReturn(Optional.of(userAlbum));

        String result = userAlbumService.updateUserAlbumRating(user.getId(), userAlbum.getAlbum().getId(), new BigDecimal("4.8"));

        assertEquals("Calificación actualizada correctamente.", result);
        verify(userAlbumRepository, times(1)).save(any(UserAlbum.class));
    }

    @Test
    void testUpdateUserAlbumRatingNotFound() {
        when(userAlbumRepository.findByUsuarioIdAndAlbumId(any(Integer.class), any(Integer.class)))
            .thenReturn(Optional.empty());

        String result = userAlbumService.updateUserAlbumRating(1, 1, new BigDecimal("4.8"));

        assertEquals("No se pudo encontrar el álbum en la lista del usuario.", result);
        verify(userAlbumRepository, times(0)).save(any(UserAlbum.class));
    }
	
}
