package com.juanromero.tfg.gestionrecursosaudiovisuales.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.test.TestRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.test.TestResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.TestFacade;

@RestController
@RequestMapping(path="/user")
public class TestController {
	@Autowired
	private TestFacade testfacade;
	@PostMapping(path="/adduser")
	public TestResponse add(@RequestBody TestRequest request) {
		return testfacade.addUser(request);
	}
	@DeleteMapping(path="/deleteuser")
	public TestResponse delete(@RequestBody TestRequest request) {
		return testfacade.deleteUser(request);
	}
	@PutMapping(path="/updateuser")
	public TestResponse update(@RequestBody TestRequest request) {
		return testfacade.updateUser(request);
	}
	@PostMapping(path="/findusers")
	public TestResponse findAllUsers() {
		return testfacade.findUser();
	}
}
