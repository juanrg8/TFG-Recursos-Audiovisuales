package com.juanromero.tfg.gestionrecursosaudiovisuales.album;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.album.AlbumRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.album.impl.AlbumServiceImpl;

public class AlbumServiceTest {

    @InjectMocks
    private AlbumServiceImpl albumService;

    @Mock
    private AlbumRepository albumRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddAlbumSuccess() {
        Album albumToAdd = new Album();
        albumToAdd.setTitle("Test Album");
        albumToAdd.setTracks(10);
        albumToAdd.setArtist("Test Artist");
        albumToAdd.setPublishDate(LocalDate.now());

        when(albumRepository.findByTitle("Test Album")).thenReturn(null);
        when(albumRepository.save(any(Album.class))).thenReturn(albumToAdd);

        String result = albumService.addAlbum(albumToAdd);

        assertEquals("Álbum guardado con éxito", result);
        verify(albumRepository, times(1)).findByTitle("Test Album");
        verify(albumRepository, times(1)).save(any(Album.class));
    }
    
    @Test
    void testAddAlbumDuplicateTitle() {
        Album existingAlbum = new Album();
        existingAlbum.setTitle("Test Album");

        when(albumRepository.findByTitle("Test Album")).thenReturn(existingAlbum);

        Album albumToAdd = new Album();
        albumToAdd.setTitle("Test Album");

        String result = albumService.addAlbum(albumToAdd);

        assertEquals("No ha sido posible guardar el álbum, título duplicado", result);
        verify(albumRepository, times(1)).findByTitle("Test Album");
        verify(albumRepository, never()).save(any(Album.class));
    }

    @Test
    void testDeleteAlbumSuccess() {
        
        Album existingAlbum = new Album();
        existingAlbum.setId(1);
        existingAlbum.setTitle("Test Album");

        when(albumRepository.findByTitle("Test Album")).thenReturn(existingAlbum);
        doNothing().when(albumRepository).delete(existingAlbum); 

        
        String result = albumService.deleteAlbum(existingAlbum);

        
        verify(albumRepository, times(2)).findByTitle("Test Album"); 
        verify(albumRepository, times(1)).delete(existingAlbum); 

        // Configurar el mock para devolver un álbum vacío después de borrar el álbum
        // Importante para que el test funcione bien, si no siempre encuentra el album
        // después de borrarlo
        when(albumRepository.findByTitle("Test Album")).thenReturn(null);

        
        String resultAfterDelete = albumService.deleteAlbum(existingAlbum);

        
        verify(albumRepository, times(3)).findByTitle("Test Album");

        
        assertEquals("Error: El álbum no se ha podido borrar.", result);
        assertEquals("Error: El álbum no existe.", resultAfterDelete); 
    }


    @Test
    void testDeleteAlbumNotFound() {
        Album albumToDelete = new Album();
        albumToDelete.setTitle("Nonexistent Album");

        when(albumRepository.findByTitle("Nonexistent Album")).thenReturn(null);

        String result = albumService.deleteAlbum(albumToDelete);

        assertEquals("Error: El álbum no existe.", result);
        verify(albumRepository, times(1)).findByTitle("Nonexistent Album");
        verify(albumRepository, never()).delete(any(Album.class));
    }
    
    @Test
    void testUpdateAlbumSuccess() {
        
        Album existingAlbum = new Album();
        existingAlbum.setId(1);
        existingAlbum.setTitle("Existing Album");

        
        when(albumRepository.findById(1)).thenReturn(Optional.of(existingAlbum));
        when(albumRepository.save(existingAlbum)).thenReturn(existingAlbum); 

        
        String result = albumService.updateAlbum(existingAlbum);

        
        verify(albumRepository, times(1)).findById(1);

        
        verify(albumRepository, times(1)).save(existingAlbum);

        
        assertEquals("El álbum se ha actualizado correctamente.", result);
    }
    
    @Test
    void testUpdateAlbumNotFound() {
        Album nonExistingAlbum = new Album();
        nonExistingAlbum.setId(2);
        nonExistingAlbum.setTitle("Non Existing Album");

        when(albumRepository.findById(2)).thenReturn(Optional.empty());

        String result = albumService.updateAlbum(nonExistingAlbum);

        verify(albumRepository, times(1)).findById(2);

        verify(albumRepository, never()).save(any(Album.class));

        assertEquals("Error: El álbum no existe.", result);
    }
    
    @Test
    void testFindAllAlbumsNotEmpty() {
        List<Album> albumList = new ArrayList<>();
        albumList.add(new Album(1));
        albumList.add(new Album(2));

        when(albumRepository.findAll()).thenReturn(albumList);

        List<Album> result = albumService.findAllAlbums();

        verify(albumRepository, times(1)).findAll();

        assertEquals(albumList.size(), result.size());
        assertEquals(albumList, result);
    }
    
    @Test
    void testFindAllAlbumsEmpty() {
        when(albumRepository.findAll()).thenReturn(Collections.emptyList());

        List<Album> result = albumService.findAllAlbums();

        verify(albumRepository, times(1)).findAll();

        assertNull(result); 
    }
    
    @Test
    void testFindAlbumsByTitleFound() {
        String title = "Test Album";
        Album foundAlbum = new Album(1);
        foundAlbum.setTitle(title);

        when(albumRepository.findByTitle(title)).thenReturn(foundAlbum);

        Album result = albumService.findAlbumsByTitle(title);

        verify(albumRepository, times(1)).findByTitle(title);

        assertNotNull(result);
        assertEquals(foundAlbum, result);
    }

    @Test
    void testFindAlbumsByTitleNotFound() {
        String title = "Non Existing Album";

        when(albumRepository.findByTitle(title)).thenReturn(null);

        Album result = albumService.findAlbumsByTitle(title);

        verify(albumRepository, times(1)).findByTitle(title);

        assertNull(result); 
    }

    @Test
    void testFindAlbumsByArtistFound() {
        String artist = "Test Artist";
        List<Album> foundAlbums = new ArrayList<>();
        foundAlbums.add(new Album(1));
        foundAlbums.add(new Album(2));

        when(albumRepository.findByArtist(artist)).thenReturn(foundAlbums);

        List<Album> result = albumService.findAlbumsByArtist(artist);

        verify(albumRepository, times(1)).findByArtist(artist);

        assertNotNull(result);
        assertEquals(foundAlbums.size(), result.size());
        assertEquals(foundAlbums, result);
    }

    @Test
    void testFindAlbumsByArtistNotFound() {
        String artist = "Non Existing Artist";

        when(albumRepository.findByArtist(artist)).thenReturn(Collections.emptyList());

        List<Album> result = albumService.findAlbumsByArtist(artist);

        verify(albumRepository, times(1)).findByArtist(artist);

        assertNull(result); 
    }

    @Test
    void testFindAlbumsByPublishDateFound() {
        String releaseDate = "2023-01-01";
        LocalDate parsedDate = LocalDate.parse(releaseDate);
        List<Album> foundAlbums = new ArrayList<>();
        foundAlbums.add(new Album(1));
        foundAlbums.add(new Album(2));

        when(albumRepository.findByReleaseDate(parsedDate)).thenReturn(foundAlbums);

        List<Album> result = albumService.findAlbumsByPublishDate(releaseDate);

        verify(albumRepository, times(1)).findByReleaseDate(parsedDate);

        assertNotNull(result);
        assertEquals(foundAlbums.size(), result.size());
        assertEquals(foundAlbums, result);
    }
	
}
