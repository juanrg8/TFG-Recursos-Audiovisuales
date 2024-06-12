package com.juanromero.tfg.gestionrecursosaudiovisuales.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserAlbumRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserAlbumResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.AlbumStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user.UserAlbumFacade;

@RestController
@RequestMapping(path = "/useralbum")
public class UserAlbumController {

	@Autowired
	private UserAlbumFacade userAlbumFacade;

	@PostMapping(path = "/add")
	public UserAlbumResponse add(@RequestBody UserAlbumRequest request) {
		UserAlbumResponse response = userAlbumFacade.addUserAlbum(request);

		return response;
	}

	@DeleteMapping(path = "/delete")
	public UserAlbumResponse delete(@RequestBody UserAlbumRequest request) {
		return userAlbumFacade.deleteUserAlbum(request);
	}

	@GetMapping(path = "/findall/{usuarioNombre}")
	public List<UserAlbumRequest> findAllUserAlbums(@PathVariable String usuarioNombre) {
		return userAlbumFacade.findAllUserAlbums(usuarioNombre).getListaUserAlbums();
	}

	@GetMapping(path = "/findbystatus/{usuarioNombre}/{status}")
	public List<UserAlbumRequest> findUserAlbumsByStatus(@PathVariable String usuarioNombre,
			@PathVariable AlbumStatus status) {
		return userAlbumFacade.findUserAlbumsByStatus(usuarioNombre, status).getListaUserAlbums();
	}

	@PutMapping(path = "/movetostatus")
	public UserAlbumResponse moveUserAlbumToStatus(@RequestBody UserAlbumRequest request) {
		return userAlbumFacade.moveUserAlbumToStatus(request.getUsuarioNombre(), request.getAlbumId());
	}

	@PutMapping(path = "/updatereview")
	public UserAlbumResponse updateUserAlbumReview(@RequestBody UserAlbumRequest request) {
		return userAlbumFacade.updateUserAlbumReview(request.getUsuarioNombre(), request.getAlbumId(), request.getReview());
	}

	@PutMapping(path = "/updaterating")
	public UserAlbumResponse updateUserAlbumRating(@RequestBody UserAlbumRequest request) {
		return userAlbumFacade.updateUserAlbumRating(request.getUsuarioNombre(), request.getAlbumId(), request.getRating());
	}
}
