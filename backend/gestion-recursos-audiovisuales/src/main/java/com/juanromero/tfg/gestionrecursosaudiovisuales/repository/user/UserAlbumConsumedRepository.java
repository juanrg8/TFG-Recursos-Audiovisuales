package com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserAlbumConsumed;

@Repository
public interface UserAlbumConsumedRepository extends JpaRepository<UserAlbumConsumed, Integer> {

	void deleteByUserAndAlbum(Integer usuarioId, Integer albumId);
    // Aquí puedes agregar métodos personalizados para consultas específicas si es necesario

	Optional<UserAlbumConsumed> findByUserAndAlbum(Integer usuarioId, Integer albumId);
}
