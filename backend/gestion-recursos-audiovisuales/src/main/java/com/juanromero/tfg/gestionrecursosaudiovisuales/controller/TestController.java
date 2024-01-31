package com.juanromero.tfg.gestionrecursosaudiovisuales.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
	public TestResponse prueba(@RequestBody TestRequest request) {
		return testfacade.testmethod(request);
	}
}
