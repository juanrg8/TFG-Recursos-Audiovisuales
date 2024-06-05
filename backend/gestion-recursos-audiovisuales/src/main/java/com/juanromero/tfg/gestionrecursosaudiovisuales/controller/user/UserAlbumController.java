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

	@GetMapping(path = "/findall/{usuarioId}")
	public List<UserAlbumRequest> findAllUserAlbums(@PathVariable Integer usuarioId) {
		return userAlbumFacade.findAllUserAlbums(usuarioId).getListaUserAlbums();
	}

	@GetMapping(path = "/findbystatus/{usuarioId}/{status}")
	public List<UserAlbumRequest> findUserAlbumsByStatus(@PathVariable Integer usuarioId, @PathVariable AlbumStatus status) {
		return userAlbumFacade.findUserAlbumsByStatus(usuarioId, status).getListaUserAlbums();
	}

	@PutMapping(path = "/movetostatus")
	public UserAlbumResponse moveUserAlbumToStatus(@RequestBody UserAlbumRequest request) {
		return userAlbumFacade.moveUserAlbumToStatus(request.getUsuarioId(), request.getAlbumId());
	}
}
