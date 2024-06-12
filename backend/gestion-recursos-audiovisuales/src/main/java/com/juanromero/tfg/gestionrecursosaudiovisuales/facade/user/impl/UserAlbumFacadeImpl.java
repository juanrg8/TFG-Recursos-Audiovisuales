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
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album.Album;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.AlbumStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserAlbum;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user.UserAlbumFacade;
import com.juanromero.tfg.gestionrecursosaudiovisuales.mapper.user.UserAlbumMapper;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.album.AlbumRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserAlbumRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.UserAlbumService;

@Service
public class UserAlbumFacadeImpl implements UserAlbumFacade {

	@Autowired
	private UserAlbumService userAlbumService;

	@Autowired
	private UserAlbumMapper userAlbumMapper;

	@Autowired
	private UserAlbumRepository userAlbumRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private AlbumRepository albumRespository; 

	@Override
	public UserAlbumResponse addUserAlbum(UserAlbumRequest userAlbumRequest) {
		UserAlbumResponse response = new UserAlbumResponse();
		String descripcionPeticion = "";
		Album album = new Album();
		album.setTitle(userAlbumRequest.getTituloAlbum());
		Album albumguardado = albumRespository.save(album);
		userAlbumRequest.setAlbumId(albumguardado.getId());
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
		Optional<User> usuarioOpt = userRepository.findByUsername(userAlbumRequest.getUsuarioNombre());
		if(usuarioOpt.isPresent()) {
		Integer idUsuario = userRepository.findByUsername(userAlbumRequest.getUsuarioNombre()).get().getId();
		descripcionPeticion = userAlbumService.deleteUserAlbum(idUsuario,
				userAlbumRequest.getAlbumId());
		response.setDescripcionPeticion(descripcionPeticion);
		}
		if (descripcionPeticion.contains("eliminado")) {
			response.setEstadoPeticion("OK");
		} else {
			response.setEstadoPeticion("KO");
		}
		return response;
	}

	@Override
	public UserAlbumResponse findAllUserAlbums(String usuarioNombre) {
		UserAlbumResponse response = new UserAlbumResponse();
		List<UserAlbumRequest> userAlbumsDTO = new ArrayList<>();
		Optional<User> userOpt = userRepository.findByUsername(usuarioNombre);
		if(userOpt.isPresent()) {
		List<UserAlbum> userAlbums = userAlbumService.findAllUserAlbums(userOpt.get().getId());
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
		}}else {
			response.setEstadoPeticion("KO");
			response.setDescripcionPeticion("No se encontró al usuario.");
		}

		return response;
	}

	@Override
	public UserAlbumResponse findUserAlbumsByStatus(String usuarioNombre, AlbumStatus status) {
		UserAlbumResponse response = new UserAlbumResponse();
		Optional<User> userOpt = userRepository.findByUsername(usuarioNombre);
		if(userOpt.isPresent()) {
		List<UserAlbum> userAlbums = userAlbumService.findUserAlbumsByStatus(userOpt.get().getId(), status);
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
		}}else {
			response.setEstadoPeticion("KO");
			response.setDescripcionPeticion("No se encontró al usuario.");
		}

		return response;
	}

	@Override
	public UserAlbumResponse moveUserAlbumToStatus(String usuarioNombre, Integer albumId) {
		UserAlbumResponse response = new UserAlbumResponse();
		Optional<User> userOpt = userRepository.findByUsername(usuarioNombre);
		if(userOpt.isPresent()) {
		
		Optional<UserAlbum> userAlbumOpt = userAlbumRepository.findByUsuarioIdAndAlbumId(userOpt.get().getId(), albumId);

		if (userAlbumOpt.isPresent()) {
			UserAlbum userAlbum = userAlbumOpt.get();

			// Guardar la fecha de escucha si se cambia de PENDING a CONSUMED
			if (userAlbum.getStatus() == AlbumStatus.PENDING) {
				userAlbum.setDateListened(LocalDate.now());
			}

			// Alternar el estado y guardar el UserAlbum
			String descripcionPeticion = userAlbumService.moveUserAlbumToStatus(userOpt.get().getId(), albumId);
			response.setDescripcionPeticion(descripcionPeticion);

			if (descripcionPeticion.contains("movido")) {
				// Construir UserAlbumRequest desde UserAlbum modificado
				UserAlbumRequest userAlbumRequest = new UserAlbumRequest(userAlbum.getId(), userAlbum.getSpotifyId(),
						userAlbum.getUsuario().getUsername(), userAlbum.getAlbum().getId(), userAlbum.getStatus(),
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
		}}else {
			response.setDescripcionPeticion("El usuario no existe.");
			response.setEstadoPeticion("KO");
		}

		return response;
	}

    @Override
    public UserAlbumResponse updateUserAlbumReview(String usuarioNombre, Integer albumId, String review) {
        UserAlbumResponse response = new UserAlbumResponse();
        String descripcionPeticion = "";
		Optional<User> userOpt = userRepository.findByUsername(usuarioNombre);
		if(userOpt.isPresent()) {
        // Lógica para actualizar la revisión del álbum de usuario
        descripcionPeticion = userAlbumService.updateUserAlbumReview(userOpt.get().getId(), albumId, review);
        response.setDescripcionPeticion(descripcionPeticion);
		}
        if (descripcionPeticion.contains("actualizado")) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }

        return response;
    }

    @Override
    public UserAlbumResponse updateUserAlbumRating(String usuarioNombre, Integer albumId, BigDecimal rating) {
        UserAlbumResponse response = new UserAlbumResponse();
        String descripcionPeticion = "";
		Optional<User> userOpt = userRepository.findByUsername(usuarioNombre);
		if(userOpt.isPresent()) {
        // Lógica para actualizar la calificación del álbum de usuario
        descripcionPeticion = userAlbumService.updateUserAlbumRating(userOpt.get().getId(), albumId, rating);
        response.setDescripcionPeticion(descripcionPeticion);
		}
        if (descripcionPeticion.contains("actualizado")) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }

        return response;
    }

}
