package com.juanromero.tfg.gestionrecursosaudiovisuales.service.videogame.impl;

import java.util.ArrayList; 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.videogame.Videogame;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.videogame.VideogameRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.videogame.VideogameService;

@Service
public class VideogameServiceImpl implements VideogameService {

	@Autowired
	private VideogameRepository videogameRepository;

	@Override
	public String addVideogame(Videogame videogame) {
		try {
			videogameRepository.save(videogame);
			return "Videojuego guardado con éxito.";
		} catch (Exception e) {
			return "Error al guardar el videojuego: " + e.getMessage();
		}
	}

	@Override
	public String deleteVideogame(Videogame videogame) {
		try {
			videogameRepository.delete(videogame);
			return "El videojuego se ha borrado correctamente.";
		} catch (Exception e) {
			return "Error al borrar el videojuego: " + e.getMessage();
		}
	}

	@Override
	public String updateVideogame(Videogame videogame) {
		try {
			videogameRepository.save(videogame);
			return "El videojuego se ha actualizado correctamente.";
		} catch (Exception e) {
			return "Error al actualizar el videojuego: " + e.getMessage();
		}
	}

	@Override
	public List<Videogame> findAllVideogames() {
		Iterable<Videogame> allVideogames = videogameRepository.findAll();
		if (allVideogames != null) {
			List<Videogame> lista = new ArrayList<>();
			for (Videogame elemento : allVideogames) {
				lista.add(elemento);
			}
			if (lista.size() > 0) {
				return lista;
			}
		}
		return null;
	}
}
