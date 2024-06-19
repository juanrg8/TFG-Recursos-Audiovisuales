package com.juanromero.tfg.gestionrecursosaudiovisuales.service.album.impl;

import java.time.LocalDate; 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album.Album;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.album.AlbumRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.album.AlbumService;

@Service
public class AlbumServiceImpl implements AlbumService {

	@Autowired
	private AlbumRepository albumRepository;

	@Override
	public String addAlbum(Album album) {
		Album existingAlbum = albumRepository.findByTitle(album.getTitle());

		if (existingAlbum == null) {
			albumRepository.save(album);
			return "Álbum guardado con éxito";
		} else {
			return "No ha sido posible guardar el álbum, título duplicado";
		}
	}

	@Override
	@Transactional
	public String deleteAlbum(Album album) {
		Album existingAlbum = albumRepository.findByTitle(album.getTitle());
		if (existingAlbum != null) {
			albumRepository.delete(existingAlbum);
			// Intentar cargar el álbum borrado nuevamente
			Album deletedAlbum = albumRepository.findByTitle(album.getTitle());
			if (deletedAlbum == null) {
				return "El álbum se ha borrado correctamente.";
			} else {
				return "Error: El álbum no se ha podido borrar.";
			}
		} else {
			return "Error: El álbum no existe.";
		}
	}

	@Override
	@Transactional
	public String updateAlbum(Album album) {
		Optional<Album> existingAlbum = albumRepository.findById(album.getId());
		if (existingAlbum.isPresent()) {
			albumRepository.save(album);
			return "El álbum se ha actualizado correctamente.";
		} else {
			return "Error: El álbum no existe.";
		}
	}

	@Override
	public List<Album> findAllAlbums() {
		Iterable<Album> allAlbums = albumRepository.findAll();
		if (allAlbums != null) {
			List<Album> albumList = new ArrayList<>();
			for (Album album : allAlbums) {
				albumList.add(album);
			}
			if (!albumList.isEmpty()) {
				return albumList;
			}
		}
		return null;
	}

	@Override
	public Album findAlbumsByTitle(String title) {
		Album album = albumRepository.findByTitle(title);
		return album;
	}

	@Override
	public List<Album> findAlbumsByArtist(String artist) {
		List<Album> albums = albumRepository.findByArtist(artist);
		return albums.isEmpty() ? null : albums;
	}

	@Override
	public List<Album> findAlbumsByPublishDate(String releaseDate) {
		LocalDate date = LocalDate.parse(releaseDate);
		List<Album> albums = albumRepository.findByReleaseDate(date);
		return albums.isEmpty() ? null : albums;
	}

}
