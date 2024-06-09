package com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.impl;

import java.util.ArrayList;    
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public String addUser(User user) {
		Optional<User> usuario = userRepository.findByUsername(user.getUsername());

		if (!usuario.isPresent()) {
			userRepository.save(user);
			return "Usuario guardado con exito";
		} else {
			return "No ha sido posible guardar el usuario, usuario duplicado";
		}

	}
	
	@Override
	@Transactional
	public String deleteUser(User user) {
	    Optional<User> usuario = userRepository.findByUsername(user.getUsername());
	    if (usuario.isPresent()) {
	        userRepository.delete(usuario.get());
	        // Intentar cargar el usuario borrado nuevamente
	        Optional<User> usuarioBorradoOpt = userRepository.findByUsername(user.getUsername());
	        if (!usuarioBorradoOpt.isPresent()) {
	            return "El usuario se ha borrado correctamente.";
	        } else {
	            return "Error: El usuario no se ha podido borrar.";
	        }
	    } else {
	        return "Error: El usuario no existe.";
	    }
	}

    
    @Override
    @Transactional
    public String updateUser(User user) {
        Optional<User> usuario = userRepository.findById(user.getId());
        if (usuario.isPresent()) {
            userRepository.save(user);
            return "El usuario se ha actualizado correctamente.";
        } else {
            return "Error: El usuario no existe.";
        }
    }

	@Override
	public List<User> findAllUsers() {
		Iterable<User> allusers = userRepository.findAll();
		if (allusers != null) {
			List<User> lista = new ArrayList<>();
			for (User elemento : allusers) {
				lista.add(elemento);
			}
			if(lista.size() > 0) {
				return lista;
			}
		}
		return null;
	}
	
}
