package com.juanromero.tfg.gestionrecursosaudiovisuales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = "com.juanromero.tfg.gestionrecursosaudiovisuales")
public class GestionRecursosAudiovisualesApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionRecursosAudiovisualesApplication.class, args);
	}

}
