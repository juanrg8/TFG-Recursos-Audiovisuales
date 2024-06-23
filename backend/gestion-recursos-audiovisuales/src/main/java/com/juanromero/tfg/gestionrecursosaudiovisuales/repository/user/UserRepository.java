package com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user;


import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;

public interface UserRepository extends  CrudRepository<User, Integer>{
	
    @Query("SELECT user FROM User user WHERE user.username LIKE :username")
    public Optional<User> findByUsername(@Param("username")String username);

    @Query("SELECT user FROM User user WHERE user.email = :email")
    public Optional<User> findByEmail(@Param("email") String email);
	
    
}

