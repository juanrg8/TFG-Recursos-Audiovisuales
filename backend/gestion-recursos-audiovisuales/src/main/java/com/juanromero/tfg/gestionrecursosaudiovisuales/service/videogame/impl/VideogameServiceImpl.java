package com.juanromero.tfg.gestionrecursosaudiovisuales.service.videogame.impl;

import java.time.LocalDate;
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
        Videogame existingVideogame = videogameRepository.findByTitle(videogame.getTitle());

        if (existingVideogame == null) {
        	videogameRepository.save(videogame);
            return "Videojuego guardado con éxito";
        } else {
            return "No ha sido posible guardar el videojuego, título duplicado";
        }
    }

	@Override
	public String deleteVideogame(Videogame videogame) {
		if (videogame.getId() != null && videogameRepository.existsById(videogame.getId())) {
			videogameRepository.delete(videogame);
			return "El videojuego se ha borrado correctamente";
		} else {
			return "Videojuego no encontrado";
		}
	}

	@Override
	public String updateVideogame(Videogame videogame) {
		if (videogame.getId() != null && videogameRepository.existsById(videogame.getId())) {
			videogameRepository.save(videogame);
			return "El videojuego se ha actualizado correctamente";
		}
		return "Videojuego no encontrado";
	}

	@Override
	public List<Videogame> findAllVideogames() {
		return (List<Videogame>) videogameRepository.findAll();
	}

	@Override
	public Videogame findByTitle(String title) {
		return videogameRepository.findByTitle(title);
	}

	@Override
	public List<Videogame> findByPlatform(String platform) {
		List<Videogame> videogames = videogameRepository.findByPlatform(platform);
		return videogames.isEmpty() ? null : videogames;
	}

	@Override
	public List<Videogame> findByGenre(String genre) {
		List<Videogame> videogames = videogameRepository.findByGenre(genre);
		return videogames.isEmpty() ? null : videogames;
	}

	@Override
	public List<Videogame> findByReleaseDate(String releaseDate) {
		LocalDate date = LocalDate.parse(releaseDate);
		List<Videogame> videogames = videogameRepository.findByReleaseDate(date);
		return videogames.isEmpty() ? null : videogames;
	}

	@Override
	public List<Videogame> findByDeveloper(String developer) {
		List<Videogame> videogames = videogameRepository.findByDeveloper(developer);
		return videogames.isEmpty() ? null : videogames;
	}

	@Override
	public List<Videogame> findByPublisher(String publisher) {
		List<Videogame> videogames = videogameRepository.findByPublisher(publisher);
		return videogames.isEmpty() ? null : videogames;
	}
}
