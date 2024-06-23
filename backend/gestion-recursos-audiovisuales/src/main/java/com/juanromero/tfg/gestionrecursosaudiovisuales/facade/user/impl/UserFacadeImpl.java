package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user.impl;

import java.util.ArrayList;  
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.ChangePasswordRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user.UserFacade;
import com.juanromero.tfg.gestionrecursosaudiovisuales.mapper.user.UserMapper;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.UserService;

@Service
public class UserFacadeImpl implements UserFacade {

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper userMapper;
	
    @Autowired
    private PasswordEncoder passwordEncoder;

	private static final String response_guardado_ok = "Usuario guardado con exito";
	private static final String response_borrado_ok = "El usuario se ha borrado correctamente.";
	private static final String response_actualizado_ok = "El usuario se ha actualizado correctamente.";
	private static final String response_encontrado_ok = "Usuarios encontrados correctamente.";
	private static final String response_password_cambiada_ok = "Contraseña cambiada con éxito.";
	@Override
	public UserResponse addUser(UserRequest userRequest) {
	    UserResponse response = new UserResponse();
	    String descripcionPeticion = "";

	    
	    String password = userRequest.getPassword();

	    
	    String encodedPassword = passwordEncoder.encode(password);

	    
	    User usuario = userMapper.dtoToEntity(userRequest);
	    usuario.setPassword(encodedPassword);

	    
	    descripcionPeticion = userService.addUser(usuario);

	    
	    response.setDescripcionPeticion(descripcionPeticion);
	    if (descripcionPeticion.equalsIgnoreCase(response_guardado_ok)) {
	        response.setEstadoPeticion("OK");
	    } else {
	        response.setEstadoPeticion("KO");
	    }
	    return response;
	}


	@Override
	public UserResponse deleteUser(UserRequest userRequest) {

		UserResponse response = new UserResponse();
		String descripcionPeticion = "";

		User usuario = userMapper.dtoToEntity(userRequest);
		descripcionPeticion = userService.deleteUser(usuario);
		response.setDescripcionPeticion(descripcionPeticion);

		if (descripcionPeticion.equalsIgnoreCase(response_borrado_ok)) {
			response.setEstadoPeticion("OK");
		} else {
			response.setEstadoPeticion("KO");
		}
		return response;
	}

	@Override
	public UserResponse updateUser(UserRequest userRequest) {
	    UserResponse response = new UserResponse();
	    String descripcionPeticion = "";
	    User usuarioPorNombre = userService.findUserByUsername(userRequest.getUsername());
	    if(usuarioPorNombre!=null) {
	    userRequest.setId(usuarioPorNombre.getId());
	    userRequest.setPassword(usuarioPorNombre.getPassword());
	    userRequest.setRol(usuarioPorNombre.getRol());
	    if(userRequest.getImage()==null||(userRequest.getImage()!=null&&userRequest.getImage().isBlank())) {
	    	userRequest.setImage(usuarioPorNombre.getImage());
	    }
	    
	    User usuario = userMapper.dtoToEntity(userRequest);
	    descripcionPeticion = userService.updateUser(usuario);
	    response.setDescripcionPeticion(descripcionPeticion);
	    }
	    if (descripcionPeticion.equalsIgnoreCase(response_actualizado_ok)) {
	        response.setEstadoPeticion("OK");
	    } else {
	        response.setEstadoPeticion("KO");
	    }
	    return response;
	}


	@Override
	public UserResponse findUser() {
		UserResponse response = new UserResponse();
		String descripcionPeticion = "No se han encontrado usuarios";
		List<UserRequest> listaUserReq = new ArrayList<>();
		List<User> lista = userService.findAllUsers();
		for (User u : lista) {
			listaUserReq.add(userMapper.entityToDto(u));
		}
		response.setListaUsuarios(listaUserReq);
		if(lista.isEmpty()) {
			response.setEstadoPeticion("KO");
			response.setDescripcionPeticion(descripcionPeticion);
		} else {
			response.setEstadoPeticion("OK");
			response.setDescripcionPeticion(response_encontrado_ok);
		}
		
		return response;
	}


	@Override
	public UserResponse findUserByUsername(String username) {
		UserResponse response = new UserResponse();
		String descripcionPeticion = "No se ha encontrado usuario";
		
		User usuario = userService.findUserByUsername(username);
		UserRequest userReq = userMapper.entityToDto(usuario);
		response.setUsuario(userReq);
		if(usuario==null) {
			response.setEstadoPeticion("KO");
			response.setDescripcionPeticion(descripcionPeticion);
		} else {
			response.setEstadoPeticion("OK");
			response.setDescripcionPeticion(response_encontrado_ok);
		}
		
		return response;
	}


    @Override
    public UserResponse changePassword(ChangePasswordRequest request) {
        UserResponse response = new UserResponse();
        String descripcionPeticion = userService.changePassword(request);
        response.setDescripcionPeticion(descripcionPeticion);
        if (descripcionPeticion.equalsIgnoreCase(response_password_cambiada_ok)) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }

}
