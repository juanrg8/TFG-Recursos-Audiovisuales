package com.juanromero.tfg.gestionrecursosaudiovisuales.album;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.juanromero.tfg.gestionrecursosaudiovisuales.controller.album.AlbumController;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.album.AlbumRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.album.AlbumResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.album.AlbumFacade;

public class AlbumControllerTest {

    @InjectMocks
    private AlbumController albumController;

    @Mock
    private AlbumFacade albumFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddAlbum() {
        AlbumRequest request = new AlbumRequest(1, "Test Album", 10, LocalDate.now(), "Test Artist", "Rock", Collections.emptyList());
        AlbumResponse expectedResponse = new AlbumResponse("success", "Album added successfully", Collections.singletonList(request));

        when(albumFacade.addAlbum(any(AlbumRequest.class))).thenReturn(expectedResponse);

        AlbumResponse response = albumController.addAlbum(request);

        assertEquals(expectedResponse, response);
        verify(albumFacade, times(1)).addAlbum(any(AlbumRequest.class));
    }
    
    @Test
    void testDeleteAlbum() {
        AlbumRequest request = new AlbumRequest(1, "Test Album", 10, LocalDate.now(), "Test Artist", "Rock", Collections.emptyList());
        AlbumResponse expectedResponse = new AlbumResponse("success", "Album deleted successfully", Collections.singletonList(request));

        when(albumFacade.deleteAlbum(any(AlbumRequest.class))).thenReturn(expectedResponse);

        AlbumResponse response = albumController.deleteAlbum(request);

        assertEquals(expectedResponse, response);
        verify(albumFacade, times(1)).deleteAlbum(any(AlbumRequest.class));
    }
    
    @Test
    void testUpdateAlbum() {
        AlbumRequest request = new AlbumRequest(1, "Test Album", 10, LocalDate.now(), "Test Artist", "Rock", Collections.emptyList());
        AlbumResponse expectedResponse = new AlbumResponse("success", "Album updated successfully", Collections.singletonList(request));

        when(albumFacade.updateAlbum(any(AlbumRequest.class))).thenReturn(expectedResponse);

        AlbumResponse response = albumController.updateAlbum(request);

        assertEquals(expectedResponse, response);
        verify(albumFacade, times(1)).updateAlbum(any(AlbumRequest.class));
    }
    
    @Test
    void testFindAllAlbums() {
        List<AlbumRequest> albumList = Collections.singletonList(
                new AlbumRequest(1, "Test Album", 10, LocalDate.now(), "Test Artist", "Rock", Collections.emptyList())
        );
        AlbumResponse expectedResponse = new AlbumResponse("success", "Albums found successfully", albumList);

        when(albumFacade.findAllAlbums()).thenReturn(expectedResponse);

        List<AlbumRequest> response = albumController.findAllAlbums();

        assertEquals(albumList.size(), response.size());
        assertEquals(expectedResponse.getListaAlbumes(), response);
        verify(albumFacade, times(1)).findAllAlbums();
    }
	
}
