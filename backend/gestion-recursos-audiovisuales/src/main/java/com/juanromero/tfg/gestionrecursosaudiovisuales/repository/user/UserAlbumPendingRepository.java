package com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserAlbumPending;

@Repository
public interface UserAlbumPendingRepository extends JpaRepository<UserAlbumPending, Integer> {

	Optional<UserAlbumPending> findByUserAndAlbum(Integer usuarioId, Integer albumId);
    // Aquí puedes agregar métodos personalizados para consultas específicas si es necesario

	void deleteByUserAndAlbum(Integer usuarioId, Integer albumId);
}
