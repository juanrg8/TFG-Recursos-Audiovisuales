package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.Rol; 

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

	private Integer id;
	private String username;
	private String nombre;
	private String bio;
	private String password;
	private String email;
	private Rol rol;
	private String image;

	public UserRequest(Integer id, String username, String nombre, String bio, String password, String email, Rol rol,String image) {
		super();

		this.setId(id);
		this.username = username;
		this.nombre = nombre;
		this.bio = bio;
		this.password = password;
		this.email = email;
		this.rol = rol;
		this.image = image;
	}

	public UserRequest() {
	}
}
