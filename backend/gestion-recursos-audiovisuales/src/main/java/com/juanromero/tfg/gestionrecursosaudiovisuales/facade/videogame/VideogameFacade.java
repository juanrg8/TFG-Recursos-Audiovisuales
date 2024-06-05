package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.videogame;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.videogame.VideogameRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.videogame.VideogameResponse;

public interface VideogameFacade {
	VideogameResponse addVideogame(VideogameRequest request);

	VideogameResponse deleteVideogame(VideogameRequest request);

	VideogameResponse updateVideogame(VideogameRequest request);

	VideogameResponse findVideogames();

	VideogameResponse findByTitle(String title);

	VideogameResponse findByPlatform(String platform);

	VideogameResponse findByGenre(String genre);

	VideogameResponse findByReleaseDate(String releaseDate);

	VideogameResponse findByDeveloper(String developer);

	VideogameResponse findByPublisher(String publisher);
}
