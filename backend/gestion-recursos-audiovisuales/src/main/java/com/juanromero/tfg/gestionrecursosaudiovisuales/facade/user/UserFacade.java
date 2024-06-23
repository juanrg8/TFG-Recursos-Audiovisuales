package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.ChangePasswordRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserResponse;

public interface UserFacade {
	
	public UserResponse addUser(UserRequest userRequest);

	public UserResponse deleteUser(UserRequest userRequest);

	public UserResponse updateUser(UserRequest userRequest);
	
	public UserResponse findUser();

	public UserResponse findUserByUsername(String username);

	public UserResponse changePassword(ChangePasswordRequest request);
}
