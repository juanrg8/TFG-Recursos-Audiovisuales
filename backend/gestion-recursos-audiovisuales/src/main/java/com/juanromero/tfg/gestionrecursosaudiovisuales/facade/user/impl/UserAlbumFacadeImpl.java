package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserAlbumRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserAlbumResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.AlbumStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserAlbum;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user.UserAlbumFacade;
import com.juanromero.tfg.gestionrecursosaudiovisuales.mapper.user.UserAlbumMapper;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserAlbumRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.UserAlbumService;

@Service
public class UserAlbumFacadeImpl implements UserAlbumFacade {

	@Autowired
	private UserAlbumService userAlbumService;

	@Autowired
	private UserAlbumMapper userAlbumMapper;

	@Autowired
	private UserAlbumRepository userAlbumRepository;

	@Override
	public UserAlbumResponse addUserAlbum(UserAlbumRequest userAlbumRequest) {
		UserAlbumResponse response = new UserAlbumResponse();
		String descripcionPeticion = "";

		UserAlbum userAlbum = userAlbumMapper.dtoToEntity(userAlbumRequest);
		descripcionPeticion = userAlbumService.addUserAlbum(userAlbum);
		response.setDescripcionPeticion(descripcionPeticion);
		userAlbumRequest.setId(userAlbum.getId());

		if (descripcionPeticion.contains("añadido")) {

			List<UserAlbumRequest> userAlbums = new ArrayList<>();
			response.setListaUserAlbums(userAlbums);
			response.getListaUserAlbums().add(userAlbumRequest);
			System.out.println(response.getListaUserAlbums());
			response.setEstadoPeticion("OK");
		} else {
			response.setEstadoPeticion("KO");
		}
		return response;
	}

	@Override
	public UserAlbumResponse deleteUserAlbum(UserAlbumRequest userAlbumRequest) {
		UserAlbumResponse response = new UserAlbumResponse();
		String descripcionPeticion = "";

		descripcionPeticion = userAlbumService.deleteUserAlbum(userAlbumRequest.getUsuarioId(),
				userAlbumRequest.getAlbumId());
		response.setDescripcionPeticion(descripcionPeticion);

		if (descripcionPeticion.contains("eliminado")) {
			response.setEstadoPeticion("OK");
		} else {
			response.setEstadoPeticion("KO");
		}
		return response;
	}

	@Override
	public UserAlbumResponse findAllUserAlbums(Integer usuarioId) {
		UserAlbumResponse response = new UserAlbumResponse();
		List<UserAlbumRequest> userAlbumsDTO = new ArrayList<>();
		List<UserAlbum> userAlbums = userAlbumService.findAllUserAlbums(usuarioId);
		for (UserAlbum ua : userAlbums) {
			userAlbumsDTO.add(userAlbumMapper.entityToDTO(ua));
		}
		response.setListaUserAlbums(userAlbumsDTO);

		if (!userAlbums.isEmpty()) {
			response.setEstadoPeticion("OK");
			response.setDescripcionPeticion("Álbumes encontrados correctamente.");
		} else {
			response.setEstadoPeticion("KO");
			response.setDescripcionPeticion("No se encontraron álbumes para el usuario.");
		}

		return response;
	}

	@Override
	public UserAlbumResponse findUserAlbumsByStatus(Integer usuarioId, AlbumStatus status) {
		UserAlbumResponse response = new UserAlbumResponse();

		List<UserAlbum> userAlbums = userAlbumService.findUserAlbumsByStatus(usuarioId, status);
		List<UserAlbumRequest> userAlbumsDTO = new ArrayList<>();
		for (UserAlbum ua : userAlbums) {
			userAlbumsDTO.add(userAlbumMapper.entityToDTO(ua));
		}
		response.setListaUserAlbums(userAlbumsDTO);

		if (!userAlbums.isEmpty()) {
			response.setEstadoPeticion("OK");
			response.setDescripcionPeticion("Álbumes encontrados correctamente.");
		} else {
			response.setEstadoPeticion("KO");
			response.setDescripcionPeticion("No se encontraron álbumes para el usuario con el estado especificado.");
		}

		return response;
	}

	@Override
	public UserAlbumResponse moveUserAlbumToStatus(Integer usuarioId, Integer albumId) {
		UserAlbumResponse response = new UserAlbumResponse();
		Optional<UserAlbum> userAlbumOpt = userAlbumRepository.findByUsuarioIdAndAlbumId(usuarioId, albumId);

		if (userAlbumOpt.isPresent()) {
			UserAlbum userAlbum = userAlbumOpt.get();

			// Guardar la fecha de escucha si se cambia de PENDING a CONSUMED
			if (userAlbum.getStatus() == AlbumStatus.PENDING) {
				userAlbum.setDateListened(LocalDate.now());
			}

			// Alternar el estado y guardar el UserAlbum
			String descripcionPeticion = userAlbumService.moveUserAlbumToStatus(usuarioId, albumId);
			response.setDescripcionPeticion(descripcionPeticion);

			if (descripcionPeticion.contains("movido")) {
				// Construir UserAlbumRequest desde UserAlbum modificado
				UserAlbumRequest userAlbumRequest = new UserAlbumRequest(userAlbum.getId(),
						userAlbum.getUsuario().getId(), userAlbum.getAlbum().getId(), userAlbum.getStatus(),
						userAlbum.getReview(), userAlbum.getRating(), userAlbum.getDateListened());

				List<UserAlbumRequest> userAlbums = new ArrayList<>();
				userAlbums.add(userAlbumRequest);
				response.setListaUserAlbums(userAlbums);

				response.setEstadoPeticion("OK");
			} else {
				response.setEstadoPeticion("KO");
			}
		} else {
			response.setDescripcionPeticion("El álbum no está en la lista.");
			response.setEstadoPeticion("KO");
		}

		return response;
	}

    @Override
    public UserAlbumResponse updateUserAlbumReview(Integer userId, Integer albumId, String review) {
        UserAlbumResponse response = new UserAlbumResponse();
        String descripcionPeticion = "";

        // Lógica para actualizar la revisión del álbum de usuario
        descripcionPeticion = userAlbumService.updateUserAlbumReview(userId, albumId, review);
        response.setDescripcionPeticion(descripcionPeticion);

        if (descripcionPeticion.contains("actualizado")) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }

        return response;
    }

    @Override
    public UserAlbumResponse updateUserAlbumRating(Integer userId, Integer albumId, BigDecimal rating) {
        UserAlbumResponse response = new UserAlbumResponse();
        String descripcionPeticion = "";

        // Lógica para actualizar la calificación del álbum de usuario
        descripcionPeticion = userAlbumService.updateUserAlbumRating(userId, albumId, rating);
        response.setDescripcionPeticion(descripcionPeticion);

        if (descripcionPeticion.contains("actualizado")) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }

        return response;
    }

}
