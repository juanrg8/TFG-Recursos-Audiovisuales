package com.juanromero.tfg.gestionrecursosaudiovisuales.repository.album;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.album.Album;

@Repository
public interface AlbumRepository extends CrudRepository<Album, Integer> {
    
    @Query("SELECT album FROM Album album WHERE album.title LIKE :title")
    public Album findByTitle(@Param("title") String title);

    @Query("SELECT a FROM Album a WHERE a.musicGenre = :genre")
    List<Album> findByGenre(@Param("genre") String musicGenre);

    @Query("SELECT a FROM Album a WHERE a.publishDate = :date")
    List<Album> findByReleaseDate(@Param("date") LocalDate date);

    @Query("SELECT a FROM Album a WHERE a.artist = :artist")
    List<Album> findByArtist(@Param("artist") String artist);
	
    @Query("SELECT a FROM Album a JOIN a.songTrack t WHERE t = :song")
    List<Album> findAlbumsBySong(@Param("song") String song);
}

