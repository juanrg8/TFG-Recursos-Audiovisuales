package com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
	private String username;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "bio")
	private String bio;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "rol")
	private Rol rol;
	
    @OneToMany(mappedBy = "usuario")
    private List<UserAlbumPending> albumesPendientes;
    
    @OneToMany(mappedBy = "usuario")
    private List<UserAlbumConsumed> albumesConsumidos;
	
	public User() {

	}

	public User(Integer id) {
		this.id = id;
	}

	public Boolean isAdmin() {
	    return this.rol == Rol.ADMIN;
	}
}
