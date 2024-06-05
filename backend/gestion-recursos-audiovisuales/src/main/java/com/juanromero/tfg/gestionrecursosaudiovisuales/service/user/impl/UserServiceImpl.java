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
		User usuario = userRepository.findByUsername(user.getUsername());

		if (usuario == null) {
			userRepository.save(user);
			return "Usuario guardado con exito";
		} else {
			return "No ha sido posible guardar el usuario, usuario duplicado";
		}

	}
	
	@Override
    @Transactional
    public String deleteUser(User user) {
        User usuario = userRepository.findByUsername(user.getUsername());
        if (usuario != null) {
            userRepository.delete(usuario);
            // Intentar cargar el usuario borrado nuevamente
            User usuarioBorrado = userRepository.findByUsername(user.getUsername());
            if (usuarioBorrado == null) {
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
