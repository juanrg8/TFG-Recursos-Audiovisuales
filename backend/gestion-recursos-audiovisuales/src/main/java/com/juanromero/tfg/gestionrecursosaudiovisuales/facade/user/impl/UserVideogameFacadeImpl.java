package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserVideogameRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserVideogameResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserVideogame;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.VideogameStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user.UserVideogameFacade;
import com.juanromero.tfg.gestionrecursosaudiovisuales.mapper.user.UserVideogameMapper;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserVideogameRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.UserVideogameService;

@Service
public class UserVideogameFacadeImpl implements UserVideogameFacade {

	@Autowired
	private UserVideogameService userVideogameService;

	@Autowired
	private UserVideogameMapper userVideogameMapper;

	@Autowired
	private UserVideogameRepository userVideogameRepository;

	@Override
	public UserVideogameResponse addUserVideogame(UserVideogameRequest userVideogameRequest) {
		UserVideogameResponse response = new UserVideogameResponse();
		String descripcionPeticion = "";
		Optional<UserVideogame> userVideogameOpt = userVideogameRepository.findByUsuarioIdAndVideogameId(userVideogameRequest.getUserId(),
				userVideogameRequest.getVideogameId());
		//Para evitar errores al mandar peticiones con usuarios o videojuegos inexistentes.
		if(!userVideogameOpt.isPresent()) {
			response.setEstadoPeticion("KO");
			response.setDescripcionPeticion("Error al crear UserVideogame, usuario o videojuego inexistente.");
			return response;
		}
		UserVideogame userVideogame = userVideogameMapper.dtoToEntity(userVideogameRequest);
		descripcionPeticion = userVideogameService.addUserVideogame(userVideogame);
		response.setDescripcionPeticion(descripcionPeticion);
		userVideogameRequest.setId(userVideogame.getId());

		if (descripcionPeticion.contains("añadido")) {
			List<UserVideogameRequest> userVideogames = new ArrayList<>();
			response.setListaUserVideogames(userVideogames);
			response.getListaUserVideogames().add(userVideogameRequest);
			response.setEstadoPeticion("OK");
		} else {
			response.setEstadoPeticion("KO");
		}
		return response;
	}

	@Override
	public UserVideogameResponse deleteUserVideogame(UserVideogameRequest userVideogameRequest) {
		UserVideogameResponse response = new UserVideogameResponse();
		String descripcionPeticion = "";

		descripcionPeticion = userVideogameService.deleteUserVideogame(userVideogameRequest.getUserId(),
				userVideogameRequest.getVideogameId());
		response.setDescripcionPeticion(descripcionPeticion);

		if (descripcionPeticion.contains("eliminado")) {
			response.setEstadoPeticion("OK");
		} else {
			response.setEstadoPeticion("KO");
		}
		return response;
	}

	@Override
	public UserVideogameResponse findAllUserVideogames(Integer usuarioId) {
		UserVideogameResponse response = new UserVideogameResponse();
		List<UserVideogameRequest> userVideogamesDTO = new ArrayList<>();
		List<UserVideogame> userVideogames = userVideogameService.findAllUserVideogames(usuarioId);
		for (UserVideogame uv : userVideogames) {
			userVideogamesDTO.add(userVideogameMapper.entityToDTO(uv));
		}
		response.setListaUserVideogames(userVideogamesDTO);

		if (!userVideogames.isEmpty()) {
			response.setEstadoPeticion("OK");
			response.setDescripcionPeticion("Videojuegos encontrados correctamente.");
		} else {
			response.setEstadoPeticion("KO");
			response.setDescripcionPeticion("No se encontraron videojuegos para el usuario.");
		}

		return response;
	}

	@Override
	public UserVideogameResponse findUserVideogamesByStatus(Integer usuarioId, VideogameStatus status) {
		UserVideogameResponse response = new UserVideogameResponse();

		List<UserVideogame> userVideogames = userVideogameService.findUserVideogamesByStatus(usuarioId, status);
		List<UserVideogameRequest> userVideogamesDTO = new ArrayList<>();
		for (UserVideogame uv : userVideogames) {
			userVideogamesDTO.add(userVideogameMapper.entityToDTO(uv));
		}
		response.setListaUserVideogames(userVideogamesDTO);

		if (!userVideogames.isEmpty()) {
			response.setEstadoPeticion("OK");
			response.setDescripcionPeticion("Videojuegos encontrados correctamente.");
		} else {
			response.setEstadoPeticion("KO");
			response.setDescripcionPeticion(
					"No se encontraron videojuegos para el usuario con el estado especificado.");
		}

		return response;
	}

	@Override
	public UserVideogameResponse moveUserVideogameToStatus(Integer usuarioId, Integer videogameId,
			VideogameStatus status) {
		UserVideogameResponse response = new UserVideogameResponse();
		Optional<UserVideogame> userVideogameOpt = userVideogameRepository.findByUsuarioIdAndVideogameId(usuarioId,
				videogameId);

		if (userVideogameOpt.isPresent()) {
			UserVideogame userVideogame = userVideogameOpt.get();

			// Guardar la fecha de inicio si se cambia a PLAYING
			if (status == VideogameStatus.PLAYING) {
				userVideogame.setDateStarted(LocalDate.now());
			}

			// Guardar la fecha de finalización si se cambia a PLAYED
			if (status == VideogameStatus.PLAYED) {
				userVideogame.setDatePlayed(LocalDate.now());
			}

			// Actualizar el estado y guardar el UserVideogame
			userVideogame.setStatus(status);
			userVideogameRepository.save(userVideogame);

			// Construir UserVideogameRequest desde UserVideogame modificado
			UserVideogameRequest userVideogameRequest = userVideogameMapper.entityToDTO(userVideogame);

			List<UserVideogameRequest> userVideogames = new ArrayList<>();
			userVideogames.add(userVideogameRequest);
			response.setListaUserVideogames(userVideogames);

			response.setEstadoPeticion("OK");
			response.setDescripcionPeticion("Videojuego movido a la lista de " + userVideogame.getStatus() + ".");
		} else {
			response.setDescripcionPeticion("El videojuego no está en la lista.");
			response.setEstadoPeticion("KO");
		}

		return response;
	}

	@Override
	public UserVideogameResponse updateUserVideogameReview(Integer userId, Integer videogameId, String review) {
		UserVideogameResponse response = new UserVideogameResponse();
		String descripcionPeticion = userVideogameService.updateUserVideogameReview(userId, videogameId, review);
		response.setDescripcionPeticion(descripcionPeticion);

		if (descripcionPeticion.contains("actualizada")) {
			response.setEstadoPeticion("OK");
		} else {
			response.setEstadoPeticion("KO");
		}
		return response;
	}

	@Override
	public UserVideogameResponse updateUserVideogameRating(Integer userId, Integer videogameId, BigDecimal rating) {
		UserVideogameResponse response = new UserVideogameResponse();
		String descripcionPeticion = userVideogameService.updateUserVideogameRating(userId, videogameId, rating);
		response.setDescripcionPeticion(descripcionPeticion);

		if (descripcionPeticion.contains("actualizada")) {
			response.setEstadoPeticion("OK");
		} else {
			response.setEstadoPeticion("KO");
		}
		return response;
	}

}
