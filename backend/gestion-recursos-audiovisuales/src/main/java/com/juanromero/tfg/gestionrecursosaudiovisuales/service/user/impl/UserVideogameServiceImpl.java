package com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserVideogame;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.VideogameStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserVideogameRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.UserVideogameService;

@Service
public class UserVideogameServiceImpl implements UserVideogameService {

    @Autowired
    private UserVideogameRepository userVideogameRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String addUserVideogame(UserVideogame userVideogame) {
        // Verificar si el videojuego ya está en la lista del usuario
        Optional<UserVideogame> existingVideogame = userVideogameRepository.findByUsuarioIdAndVideogameId(userVideogame.getUsuario().getId(), userVideogame.getVideogame().getId());
        if(existingVideogame.get().getVideogame()==null) {
        	return "El videojuego no existe!";
        }
        
        if(existingVideogame.get().getUsuario()==null) {
        	return "El usuario no existe!";
        }
        
        if (existingVideogame.isPresent()) {
            return "El videojuego ya está en la lista del usuario.";
        }

        // Guardar el UserVideogame
        userVideogameRepository.save(userVideogame);

        // Añadir el UserVideogame a la lista del usuario
        User user = userVideogame.getUsuario();
        user.getUserVideogames().add(userVideogame);
        userRepository.save(user);

        return "Videojuego añadido a la lista del usuario.";
    }

    @Transactional
    public String deleteUserVideogame(Integer usuarioId, Integer videogameId) {
        // Verificar si el videojuego está en la lista del usuario
        Optional<UserVideogame> existingVideogame = userVideogameRepository.findByUsuarioIdAndVideogameId(usuarioId, videogameId);
        if (!existingVideogame.isPresent()) {
            return "El videojuego no está en la lista del usuario.";
        }

        // Eliminar el UserVideogame
        UserVideogame userVideogame = existingVideogame.get();
        userVideogameRepository.delete(userVideogame);

        // Eliminar el UserVideogame de la lista del usuario
        User user = userVideogame.getUsuario();
        user.getUserVideogames().remove(userVideogame);
        userRepository.save(user);

        return "Videojuego eliminado de la lista del usuario.";
    }

    @Override
    public List<UserVideogame> findAllUserVideogames(Integer usuarioId) {
        return userVideogameRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<UserVideogame> findUserVideogamesByStatus(Integer usuarioId, VideogameStatus status) {
        return userVideogameRepository.findByUsuarioIdAndStatus(usuarioId, status);
    }

    @Override
    public String moveUserVideogameToStatus(Integer usuarioId, Integer videogameId, VideogameStatus status) {
        Optional<UserVideogame> userVideogameOpt = userVideogameRepository.findByUsuarioIdAndVideogameId(usuarioId, videogameId);
        if (userVideogameOpt.isPresent()) {
            UserVideogame userVideogame = userVideogameOpt.get();
            userVideogame.setStatus(status);

            // Establecer fechas según el estado
            if (status == VideogameStatus.PLAYING) {
                userVideogame.setDateStarted(LocalDate.now());
            } else if (status == VideogameStatus.PLAYED) {
                userVideogame.setDatePlayed(LocalDate.now());
            }

            userVideogameRepository.save(userVideogame);
            return "Videojuego movido a " + status + ".";
        }
        return "El videojuego no está en la lista.";
    }

    @Override
    public UserVideogame findUserVideogameById(Integer id) {
        return userVideogameRepository.findById(id).orElse(null); 
    }

	@Override
	public String updateUserVideogameReview(Integer userId, Integer videogameId, String review) {
		Optional<UserVideogame> userVideogameOpt = userVideogameRepository.findByUsuarioIdAndVideogameId(userId, videogameId);
        if (userVideogameOpt.isPresent()) {
            UserVideogame userVideogame = userVideogameOpt.get();
            userVideogame.setReview(review);
            userVideogameRepository.save(userVideogame);
            return "Revisión del videojuego actualizada.";
        }
        return "No se pudo encontrar el videojuego en la lista del usuario.";
	}

	@Override
	public String updateUserVideogameRating(Integer userId, Integer videogameId, BigDecimal rating) {
		Optional<UserVideogame> userVideogameOpt = userVideogameRepository.findByUsuarioIdAndVideogameId(userId, videogameId);
        if (userVideogameOpt.isPresent()) {
            UserVideogame userVideogame = userVideogameOpt.get();
            userVideogame.setRating(rating);
            userVideogameRepository.save(userVideogame);
            return "Calificación del videojuego actualizada.";
        }
        return "No se pudo encontrar el videojuego en la lista del usuario.";
	}
}
