package com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserAlbumConsumedRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserAlbumPendingRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album.Album;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserAlbumConsumed;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserAlbumPending;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.album.AlbumRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserAlbumConsumedRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserAlbumPendingRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.UserAlbumService;

@Service
public class UserAlbumServiceImpl implements UserAlbumService {

    @Autowired
    private UserAlbumPendingRepository userAlbumPendingRepository;

    @Autowired
    private UserAlbumConsumedRepository userAlbumConsumedRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AlbumRepository albumRepository;
    
    @Override
    public String addAlbumToPendingList(UserAlbumPendingRequest request) {
        // Verificar si el álbum ya está en la lista de pendientes
        Optional<UserAlbumPending> existingAlbum = userAlbumPendingRepository.findByUserAndAlbum(request.getUsuarioId(), request.getAlbumId());
        User usuario = userRepository.findById(request.getUsuarioId()).orElse(null);
        Album album = albumRepository.findById(request.getUsuarioId()).orElse(null);
        
        if (existingAlbum.isPresent()) {
            return "El álbum ya está en la lista de pendientes.";
        }

        if(usuario == null | album == null) {
        	return "No existe el album o usuario";
        }else {
        // Agregar el álbum a la lista de pendientes
        UserAlbumPending userAlbumPending = new UserAlbumPending();
        userAlbumPending.setUsuario(usuario);
        userAlbumPending.setAlbum(album);
        userAlbumPendingRepository.save(userAlbumPending);
        }
        return "Álbum añadido a la lista de pendientes.";
    }

    @Override
    public String addAlbumToConsumedList(UserAlbumConsumedRequest request) {
        // Verificar si el álbum ya está en la lista de consumidos
        Optional<UserAlbumConsumed> existingAlbum = userAlbumConsumedRepository.findByUserAndAlbum(request.getUsuarioId(), request.getAlbumId());
        User usuario = userRepository.findById(request.getUsuarioId()).orElse(null);
        Album album = albumRepository.findById(request.getUsuarioId()).orElse(null);
        
        if (existingAlbum.isPresent()) {
            return "El álbum ya está en la lista de consumidos.";
        }
        if(usuario == null | album == null) {
        	return "No existe el album o usuario";
        }else {
        // Agregar el álbum a la lista de consumidos
        UserAlbumConsumed userAlbumConsumed = new UserAlbumConsumed();
        userAlbumConsumed.setUsuario(usuario);
        userAlbumConsumed.setAlbum(album);
        userAlbumConsumedRepository.save(userAlbumConsumed);
        }
        // Verificar y eliminar el álbum de la lista de pendientes si es necesario
        userAlbumPendingRepository.deleteByUserAndAlbum(request.getUsuarioId(), request.getAlbumId());

        return "Álbum añadido a la lista de consumidos.";
    }

    @Override
    public String deleteAlbumFromPendingList(UserAlbumPendingRequest request) {
        // Eliminar el álbum de la lista de pendientes
        userAlbumPendingRepository.deleteByUserAndAlbum(request.getUsuarioId(), request.getAlbumId());
        return "Álbum eliminado de la lista de pendientes.";
    }

    @Override
    public String deleteAlbumFromConsumedList(UserAlbumConsumedRequest request) {
        // Eliminar el álbum de la lista de consumidos
        userAlbumConsumedRepository.deleteByUserAndAlbum(request.getUsuarioId(), request.getAlbumId());
        return "Álbum eliminado de la lista de consumidos.";
    }
}
