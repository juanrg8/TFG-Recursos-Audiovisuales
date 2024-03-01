package com.juanromero.tfg.gestionrecursosaudiovisuales.facade;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserResponse;

public interface UserFacade {
	
	public UserResponse addUser(UserRequest test);

	public UserResponse deleteUser(UserRequest test);

	public UserResponse updateUser(UserRequest test);
	
	public UserResponse findUser();
}
