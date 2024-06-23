package com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user;


import java.util.List; 

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
    @Column(name = "username")
    @NotNull(message = "Username cannot be null")
    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;
    
    @Column(name = "nombre")
    @NotNull(message = "Nombre cannot be null")
    @NotEmpty(message = "Nombre cannot be empty")
    @Size(min = 3, max = 100, message = "Nombre must be between 3 and 100 characters")
    private String nombre;
    
    @Column(name = "bio")
    @Size(max = 255, message = "Bio must be less than 255 characters")
    private String bio;
    
    @Column(name = "password")
    @NotNull(message = "Password cannot be null")
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    
    @Column(name = "email")
    @NotNull(message = "Email cannot be null")
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;
    
    @Column(name = "rol")
    @NotNull(message = "Role cannot be null")
    private Rol rol;
    
    @Column(name = "image")
    private String image;
	
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAlbum> userAlbums;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserVideogame> userVideogames;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserBook> userBooks;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserMovie> userMovies;
    
    
	public User() {

	}

	public User(Integer id) {
		this.id = id;
	}

	public Boolean isAdmin() {
	    return this.rol == Rol.ADMIN;
	}
}
