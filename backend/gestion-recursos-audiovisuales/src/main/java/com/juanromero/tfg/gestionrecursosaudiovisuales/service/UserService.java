package com.juanromero.tfg.gestionrecursosaudiovisuales.service;


import java.util.List;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;


public interface UserService {
	
	public String addUser(User user);
	
	public String deleteUser(User user);

	public String updateUser(User usuario);

	public List<User> findAllUsers();

	

}
