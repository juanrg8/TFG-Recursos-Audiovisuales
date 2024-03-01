package com.juanromero.tfg.gestionrecursosaudiovisuales.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.UserFacade;

@RestController
@RequestMapping(path="/user")
public class UserController {
	@Autowired
	private UserFacade testfacade;
	
	@PostMapping(path="/adduser")
	public UserResponse add(@RequestBody UserRequest request) {
		return testfacade.addUser(request);
	}
	@DeleteMapping(path="/deleteuser")
	public UserResponse delete(@RequestBody UserRequest request) {
		return testfacade.deleteUser(request);
	}
	@PutMapping(path="/updateuser")
	public UserResponse update(@RequestBody UserRequest request) {
		return testfacade.updateUser(request);
	}
	@GetMapping(path="/findusers")
	public UserResponse findAllUsers() {
		return testfacade.findUser();
	}
}
