package com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.BookStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserBook;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook, Integer> {

    @Query("DELETE FROM UserBook ub WHERE ub.usuario.id = :usuarioId AND ub.book.id = :bookId")
    void deleteByUsuarioIdAndBookId(@Param("usuarioId") Integer usuarioId, @Param("bookId") Integer bookId);

    @Query("SELECT ub FROM UserBook ub WHERE ub.usuario.id = :usuarioId AND ub.book.id = :bookId")
    Optional<UserBook> findByUsuarioIdAndBookId(@Param("usuarioId") Integer usuarioId, @Param("bookId") Integer bookId);

    @Query("SELECT ub FROM UserBook ub WHERE ub.usuario.id = :usuarioId")
    List<UserBook> findByUsuarioId(@Param("usuarioId") Integer usuarioId);

    @Query("SELECT CASE WHEN COUNT(ub) > 0 THEN TRUE ELSE FALSE END FROM UserBook ub WHERE ub.usuario.id = :usuarioId AND ub.book.id = :bookId")
    boolean existsByUsuarioAndBook(@Param("usuarioId") Integer usuarioId, @Param("bookId") Integer bookId);

    @Query("SELECT ub FROM UserBook ub WHERE ub.usuario.id = :usuarioId AND ub.status = :status")
    List<UserBook> findByUsuarioIdAndStatus(@Param("usuarioId") Integer usuarioId, @Param("status") BookStatus status);
}
