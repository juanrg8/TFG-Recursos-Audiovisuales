package com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.AlbumStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserAlbum;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserAlbumRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.UserAlbumService;

@Service
public class UserAlbumServiceImpl implements UserAlbumService {

    @Autowired
    private UserAlbumRepository userAlbumRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String addUserAlbum(UserAlbum userAlbum) {
        // Verificar si el álbum ya está en la lista del usuario
        Optional<UserAlbum> existingAlbum = userAlbumRepository.findByUsuarioIdAndAlbumId(userAlbum.getUsuario().getId(), userAlbum.getAlbum().getId());
        if (existingAlbum.isPresent()) {
            return "El álbum ya está en la lista del usuario.";
        }

        // Guardar el UserAlbum
        userAlbumRepository.save(userAlbum);

        // Añadir el UserAlbum a la lista del usuario
        User user = userAlbum.getUsuario();
        user.getUserAlbums().add(userAlbum);
        userRepository.save(user);

        return "Álbum añadido a la lista del usuario.";
    }

    @Transactional
    public String deleteUserAlbum(Integer usuarioId, Integer albumId) {
        // Verificar si el álbum está en la lista del usuario
        Optional<UserAlbum> existingAlbum = userAlbumRepository.findByUsuarioIdAndAlbumId(usuarioId, albumId);
        if (!existingAlbum.isPresent()) {
            return "El álbum no está en la lista del usuario.";
        }

        // Eliminar el UserAlbum
        UserAlbum userAlbum = existingAlbum.get();
        userAlbumRepository.delete(userAlbum);

        // Eliminar el UserAlbum de la lista del usuario
        User user = userAlbum.getUsuario();
        user.getUserAlbums().remove(userAlbum);
        userRepository.save(user);

        return "Álbum eliminado de la lista del usuario.";
    }


    @Override
    public List<UserAlbum> findAllUserAlbums(Integer usuarioId) {
        return userAlbumRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<UserAlbum> findUserAlbumsByStatus(Integer usuarioId, AlbumStatus status) {
        return userAlbumRepository.findByUsuarioIdAndStatus(usuarioId, status);
    }

    @Override
    public String moveUserAlbumToStatus(Integer usuarioId, Integer albumId) {
        Optional<UserAlbum> userAlbumOpt = userAlbumRepository.findByUsuarioIdAndAlbumId(usuarioId, albumId);
        if (userAlbumOpt.isPresent()) {
            UserAlbum userAlbum = userAlbumOpt.get();
            AlbumStatus status = userAlbum.getStatus();
            if(status.equals(AlbumStatus.PENDING)) {
            	userAlbum.setStatus(AlbumStatus.CONSUMED);
            }else {
            	userAlbum.setStatus(AlbumStatus.PENDING);
            }
            userAlbumRepository.save(userAlbum);
            return "Álbum movido a la lista de " + userAlbum.getStatus() + ".";
        }
        return "El álbum no está en la lista.";
    }

    @Override
    public UserAlbum findUserAlbumById(Integer id) {
        return userAlbumRepository.findById(id).orElse(null); 
    }

    @Override
    @Transactional
    public String updateUserAlbumReview(Integer userId, Integer albumId, String review) {
        Optional<UserAlbum> userAlbumOpt = userAlbumRepository.findByUsuarioIdAndAlbumId(userId, albumId);
        if (userAlbumOpt.isPresent()) {
            UserAlbum userAlbum = userAlbumOpt.get();
            userAlbum.setReview(review);
            userAlbumRepository.save(userAlbum);
            return "Revisión actualizada correctamente.";
        }
        return "No se pudo encontrar el álbum en la lista del usuario.";
    }

    @Override
    @Transactional
    public String updateUserAlbumRating(Integer userId, Integer albumId, BigDecimal rating) {
        Optional<UserAlbum> userAlbumOpt = userAlbumRepository.findByUsuarioIdAndAlbumId(userId, albumId);
        if (userAlbumOpt.isPresent()) {
            UserAlbum userAlbum = userAlbumOpt.get();
            userAlbum.setRating(rating);
            userAlbumRepository.save(userAlbum);
            return "Calificación actualizada correctamente.";
        }
        return "No se pudo encontrar el álbum en la lista del usuario.";
    }
}
