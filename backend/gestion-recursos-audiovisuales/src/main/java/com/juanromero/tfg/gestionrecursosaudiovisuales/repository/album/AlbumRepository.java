package com.juanromero.tfg.gestionrecursosaudiovisuales.repository.album;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album.Album;

@Repository
public interface AlbumRepository extends CrudRepository<Album, Integer> {
    
    @Query("SELECT album FROM Album album WHERE album.title LIKE :title")
    public Album findByTitle(@Param("title") String title);
}

