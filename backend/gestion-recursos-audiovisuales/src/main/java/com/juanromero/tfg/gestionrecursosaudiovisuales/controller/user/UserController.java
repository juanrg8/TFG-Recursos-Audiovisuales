package com.juanromero.tfg.gestionrecursosaudiovisuales.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.ChangePasswordRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user.UserFacade;

@RestController
@RequestMapping(path="/user")
public class UserController {
	@Autowired
	private UserFacade userFacade;
	
	@PostMapping(path="/adduser")
	public UserResponse add(@RequestBody UserRequest request) {
		return userFacade.addUser(request);
	}
	@DeleteMapping(path="/deleteuser")
	public UserResponse delete(@RequestBody UserRequest request) {
		return userFacade.deleteUser(request);
	}
	@PutMapping(path="/updateuser")
	public UserResponse update(@RequestBody UserRequest request) {
		return userFacade.updateUser(request);
	}
	@GetMapping(path="/findusers")
	public UserResponse findAllUsers() {
		return userFacade.findUser();
	}
	@GetMapping(path="/finduser/{usuarioNombre}")
	public UserResponse findUsers(@PathVariable String usuarioNombre) {
		return userFacade.findUserByUsername(usuarioNombre);
	}
	
    @PutMapping("/changePassword")
    public UserResponse changePassword(@RequestBody ChangePasswordRequest request) {
        return userFacade.changePassword(request);
    }
}
