package com.juanromero.tfg.gestionrecursosaudiovisuales.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserBookRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserBookResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.BookStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user.UserBookFacade;

@RestController
@RequestMapping(path = "/userbook")
public class UserBookController {

    @Autowired
    private UserBookFacade userBookFacade;

    @PostMapping(path = "/add")
    public UserBookResponse add(@RequestBody UserBookRequest request) {
        return userBookFacade.addUserBook(request);
    }

    @DeleteMapping(path = "/delete")
    public UserBookResponse delete(@RequestBody UserBookRequest request) {
        return userBookFacade.deleteUserBook(request);
    }

    @GetMapping(path = "/findall/{usuarioId}")
    public List<UserBookRequest> findAllUserBooks(@PathVariable Integer usuarioId) {
        return userBookFacade.findAllUserBooks(usuarioId).getListaUserBooks();
    }

    @GetMapping(path = "/findbystatus/{usuarioId}/{status}")
    public List<UserBookRequest> findUserBooksByStatus(@PathVariable Integer usuarioId, @PathVariable BookStatus status) {
        return userBookFacade.findUserBooksByStatus(usuarioId, status).getListaUserBooks();
    }

    @PutMapping(path = "/movetostatus")
    public UserBookResponse moveUserBookToStatus(@RequestBody UserBookRequest request) {
        return userBookFacade.moveUserBookToStatus(request.getUserId(), request.getBookId(), request.getStatus());
    }

    @PutMapping(path = "/updatereview")
    public UserBookResponse updateUserBookReview(@RequestBody UserBookRequest request) {
        return userBookFacade.updateUserBookReview(request.getUserId(), request.getBookId(), request.getReview());
    }

    @PutMapping(path = "/updaterating")
    public UserBookResponse updateUserBookRating(@RequestBody UserBookRequest request) {
        return userBookFacade.updateUserBookRating(request.getUserId(), request.getBookId(), request.getRating());
    }
}
