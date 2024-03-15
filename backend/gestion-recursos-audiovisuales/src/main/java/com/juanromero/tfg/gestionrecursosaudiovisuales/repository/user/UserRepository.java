package com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;

public interface UserRepository extends  CrudRepository<User, Integer>{
	
    @Query("SELECT user FROM User user WHERE user.username LIKE :username")
    public User findByUsername(@Param("username")String username);
	
    
}

