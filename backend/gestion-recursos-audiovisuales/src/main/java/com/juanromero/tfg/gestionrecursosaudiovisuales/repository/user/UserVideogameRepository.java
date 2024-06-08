package com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserVideogame;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.VideogameStatus;

@Repository
public interface UserVideogameRepository extends JpaRepository<UserVideogame, Integer> {

    @Query("DELETE FROM UserVideogame uv WHERE uv.usuario.id = :usuarioId AND uv.videogame.id = :videogameId")
    void deleteByUsuarioIdAndVideogameId(@Param("usuarioId") Integer usuarioId, @Param("videogameId") Integer videogameId);

    @Query("SELECT uv FROM UserVideogame uv WHERE uv.usuario.id = :usuarioId AND uv.videogame.id = :videogameId")
    Optional<UserVideogame> findByUsuarioIdAndVideogameId(@Param("usuarioId") Integer usuarioId, @Param("videogameId") Integer videogameId);

    @Query("SELECT uv FROM UserVideogame uv WHERE uv.usuario.id = :usuarioId AND uv.status = :status")
    List<UserVideogame> findByUsuarioIdAndStatus(@Param("usuarioId") Integer usuarioId, @Param("status") VideogameStatus status);
    
    @Query("SELECT uv FROM UserVideogame uv WHERE uv.usuario.id = :usuarioId")
    List<UserVideogame> findByUsuarioId(@Param("usuarioId") Integer usuarioId);

    @Query("SELECT CASE WHEN COUNT(uv) > 0 THEN TRUE ELSE FALSE END FROM UserVideogame uv WHERE uv.usuario.id = :usuarioId AND uv.videogame.id = :videogameId")
    boolean existsByUsuarioAndVideogame(@Param("usuarioId") Integer usuarioId, @Param("videogameId") Integer videogameId);
}
