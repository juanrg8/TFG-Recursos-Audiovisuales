package com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album.Album;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.AlbumStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserAlbum;

@Repository
public interface UserAlbumRepository extends JpaRepository<UserAlbum, Integer> {

    @Query("DELETE FROM UserAlbum ua WHERE ua.usuario.id = :usuarioId AND ua.album.id = :albumId")
    void deleteByUsuarioIdAndAlbumId(@Param("usuarioId") Integer usuarioId, @Param("albumId") Integer albumId);

    @Query("SELECT ua FROM UserAlbum ua WHERE ua.usuario.id = :usuarioId AND ua.album.id = :albumId")
    Optional<UserAlbum> findByUsuarioIdAndAlbumId(@Param("usuarioId") Integer usuarioId, @Param("albumId") Integer albumId);

    @Query("SELECT ua FROM UserAlbum ua WHERE ua.usuario.id = :usuarioId AND ua.status = :status")
    List<UserAlbum> findByUsuarioIdAndStatus(@Param("usuarioId") Integer usuarioId, @Param("status") AlbumStatus status);
    
    @Query("SELECT ua FROM UserAlbum ua WHERE ua.usuario.id = :usuarioId")
    List<UserAlbum> findByUsuarioId(@Param("usuarioId") Integer usuarioId);
    
    @Query("SELECT CASE WHEN COUNT(ua) > 0 THEN TRUE ELSE FALSE END FROM UserAlbum ua WHERE ua.usuario = :usuario AND ua.album = :album")
    boolean existsByUsuarioAndAlbum(@Param("usuario") User usuario, @Param("album") Album album);
}
