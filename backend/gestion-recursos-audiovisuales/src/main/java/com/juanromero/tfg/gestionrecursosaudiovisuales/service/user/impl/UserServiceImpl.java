package com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.impl;

import java.util.ArrayList; 
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.ChangePasswordRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.Rol;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.UserService;




@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

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
	    	if(usuario.get().getRol()!= Rol.ADMIN) {
	        userRepository.delete(usuario.get());
	    	}else {
	    		return "No se puede eliminar a un administrador";
	    	}
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

	@Override
	public User findUserByUsername(String username) {
		Optional<User> usuarioOpt = userRepository.findByUsername(username);
		if(usuarioOpt.isPresent()) {
			return usuarioOpt.get();
		}
		return null;
	}
	
	@Override
	@Transactional
	public String changePassword(ChangePasswordRequest request) {
	    // Buscar el usuario por nombre de usuario
	    Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
	    if (optionalUser.isPresent()) {
	        User user = optionalUser.get();
	        
	        // Verificar que la contraseña actual sea correcta antes de cambiarla
	        if (passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
	            // Codificar la nueva contraseña
	            String encodedPassword = passwordEncoder.encode(request.getNewPassword());
	            user.setPassword(encodedPassword);
	            
	            // Guardar el usuario actualizado
	            userRepository.save(user);

	            return "Contraseña cambiada con éxito.";
	        } else {
	            return "Error: La contraseña actual no es correcta";
	        }
	    } else {
	        return "Error: El usuario no existe";
	    }
	}
	
}
