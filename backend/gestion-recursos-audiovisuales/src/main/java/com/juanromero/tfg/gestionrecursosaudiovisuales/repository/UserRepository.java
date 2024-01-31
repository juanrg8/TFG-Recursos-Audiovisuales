package com.juanromero.tfg.gestionrecursosaudiovisuales.repository;

import org.springframework.data.repository.CrudRepository;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;

public interface UserRepository extends  CrudRepository<User, String>{
	
    
}

