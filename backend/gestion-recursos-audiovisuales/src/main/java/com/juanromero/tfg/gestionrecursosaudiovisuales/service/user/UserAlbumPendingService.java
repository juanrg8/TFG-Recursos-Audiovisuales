package com.juanromero.tfg.gestionrecursosaudiovisuales.service.user;

import java.util.List;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserAlbumPending;

public interface UserAlbumPendingService {
    
    public String addUserAlbumPending(UserAlbumPending userAlbumPending);
    
    public String deleteUserAlbumPending(UserAlbumPending userAlbumPending);

    public String updateUserAlbumPending(UserAlbumPending userAlbumPending);

    public List<UserAlbumPending> findAllUserAlbumsPending();
    
    public List<UserAlbumPending> findUserAlbumsPendingByUserId(Integer userId);

}
