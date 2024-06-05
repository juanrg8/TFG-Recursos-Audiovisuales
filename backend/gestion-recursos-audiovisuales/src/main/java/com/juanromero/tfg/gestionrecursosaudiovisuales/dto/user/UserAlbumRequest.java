package com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.AlbumStatus; 




public class UserAlbumRequest {

    private Integer id;
    private Integer usuarioId;
    private Integer albumId;
    private AlbumStatus status;

    public UserAlbumRequest(Integer id, Integer usuarioId, Integer albumId, AlbumStatus status) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.albumId = albumId;
        this.status = status;
    }
    
    public UserAlbumRequest() {
    	
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Integer albumId) {
		this.albumId = albumId;
	}

	public AlbumStatus getStatus() {
		return status;
	}

	public void setStatus(AlbumStatus status) {
		this.status = status;
	}
    
    
    
    
}

