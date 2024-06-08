package com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.MovieStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserMovie;

@Repository
public interface UserMovieRepository extends JpaRepository<UserMovie, Integer> {

    @Query("DELETE FROM UserMovie um WHERE um.usuario.id = :usuarioId AND um.movie.id = :movieId")
    void deleteByUsuarioIdAndMovieId(@Param("usuarioId") Integer usuarioId, @Param("movieId") Integer movieId);

    @Query("SELECT um FROM UserMovie um WHERE um.usuario.id = :usuarioId AND um.movie.id = :movieId")
    Optional<UserMovie> findByUsuarioIdAndMovieId(@Param("usuarioId") Integer usuarioId, @Param("movieId") Integer movieId);

    @Query("SELECT um FROM UserMovie um WHERE um.usuario.id = :usuarioId")
    List<UserMovie> findByUsuarioId(@Param("usuarioId") Integer usuarioId);

    @Query("SELECT CASE WHEN COUNT(um) > 0 THEN TRUE ELSE FALSE END FROM UserMovie um WHERE um.usuario.id = :usuarioId AND um.movie.id = :movieId")
    boolean existsByUsuarioAndMovie(@Param("usuarioId") Integer usuarioId, @Param("movieId") Integer movieId);
    
    @Query("SELECT um FROM UserMovie um WHERE um.usuario.id = :usuarioId AND um.status = :status")
    List<UserMovie> findByUsuarioIdAndStatus(@Param("usuarioId") Integer usuarioId, @Param("status") MovieStatus status);
}
