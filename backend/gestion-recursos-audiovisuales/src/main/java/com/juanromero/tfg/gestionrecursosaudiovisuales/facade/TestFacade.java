package com.juanromero.tfg.gestionrecursosaudiovisuales.facade;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.test.TestRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.test.TestResponse;

public interface TestFacade {
	
	public TestResponse addUser(TestRequest test);

	public TestResponse deleteUser(TestRequest test);

	public TestResponse updateUser(TestRequest test);
	
	public TestResponse findUser();
}
