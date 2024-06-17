package com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user.impl;

import java.math.BigDecimal; 
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserBookRequest;
import com.juanromero.tfg.gestionrecursosaudiovisuales.dto.user.UserBookResponse;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.book.Book;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.BookStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserBook;
import com.juanromero.tfg.gestionrecursosaudiovisuales.facade.user.UserBookFacade;
import com.juanromero.tfg.gestionrecursosaudiovisuales.mapper.user.UserBookMapper;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.book.BookRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserBookRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.UserBookService;

@Service
public class UserBookFacadeImpl implements UserBookFacade {

    @Autowired
    private UserBookService userBookService;

    @Autowired
    private UserBookMapper userBookMapper;

    @Autowired
    private UserBookRepository userBookRepository;
    
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BookRepository bookRepository;

    @Override
    public UserBookResponse addUserBook(UserBookRequest userBookRequest) {
        UserBookResponse response = new UserBookResponse();
        String descripcionPeticion = "";

		Book book = new Book();
		book.setTitle(userBookRequest.getTituloLibro());
		book.setPages(userBookRequest.getPaginas());
		book.setPublishDate(userBookRequest.getPublishDate());
		book.setAuthor(userBookRequest.getAuthor());
		book.setGenre(userBookRequest.getGenre());
		Book libroTitulo = bookRepository.findByTitle(book.getTitle());
		if(libroTitulo==null) {
			Book libroguardado = bookRepository.save(book);
			userBookRequest.setBookId(libroguardado.getId());
		}else {
			userBookRequest.setBookId(libroTitulo.getId());
		}
        
        UserBook userBook = userBookMapper.dtoToEntity(userBookRequest);
        descripcionPeticion = userBookService.addUserBook(userBook);
        response.setDescripcionPeticion(descripcionPeticion);
        userBookRequest.setId(userBook.getId());

        if (descripcionPeticion.contains("añadido")) {
            List<UserBookRequest> userBooks = new ArrayList<>();
            response.setListaUserBooks(userBooks);
            response.getListaUserBooks().add(userBookRequest);
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }

    @Override
    public UserBookResponse deleteUserBook(UserBookRequest userBookRequest) {
        UserBookResponse response = new UserBookResponse();
        String descripcionPeticion = "";
		Optional<User> usuarioOpt = userRepository.findByUsername(userBookRequest.getUsuarioNombre());
		Book book = bookRepository.findByTitle(userBookRequest.getTituloLibro());
		if(usuarioOpt.isPresent()&&book!=null) {
		Integer idUsuario = userRepository.findByUsername(userBookRequest.getUsuarioNombre()).get().getId();
		descripcionPeticion = userBookService.deleteUserBook(idUsuario,
				book.getId());
		response.setDescripcionPeticion(descripcionPeticion);
		}

        if (descripcionPeticion.contains("eliminado")) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }

    @Override
    public UserBookResponse findAllUserBooks(String usuarioNombre) {
        UserBookResponse response = new UserBookResponse();
        List<UserBookRequest> userBooksDTO = new ArrayList<>();
        Optional<User> userOpt = userRepository.findByUsername(usuarioNombre);
        if(userOpt.isPresent()) {
        List<UserBook> userBooks = userBookService.findAllUserBooks(userOpt.get().getId());
        for (UserBook ub: userBooks) {
            userBooksDTO.add(userBookMapper.entityToDto(ub));
        }
        response.setListaUserBooks(userBooksDTO);

        if (!userBooks.isEmpty()) {
            response.setEstadoPeticion("OK");
            response.setDescripcionPeticion("Libros encontrados correctamente.");
        } else {
            response.setEstadoPeticion("KO");
            response.setDescripcionPeticion("No se encontraron libros para el usuario.");
        }}else {
			response.setEstadoPeticion("KO");
			response.setDescripcionPeticion("No se encontró al usuario.");
        }

        return response;
    }

    @Override
    public UserBookResponse findUserBooksByStatus(String usuarioNombre, BookStatus status) {
        UserBookResponse response = new UserBookResponse();
		Optional<User> userOpt = userRepository.findByUsername(usuarioNombre);
		if(userOpt.isPresent()) {
        List<UserBook> userBooks = userBookService.findUserBooksByStatus(userOpt.get().getId(), status);
        List<UserBookRequest> userBooksDTO = new ArrayList<>();
        for (UserBook ub: userBooks) {
            userBooksDTO.add(userBookMapper.entityToDto(ub));
        }
        response.setListaUserBooks(userBooksDTO);

        if (!userBooks.isEmpty()) {
            response.setEstadoPeticion("OK");
            response.setDescripcionPeticion("Libros encontrados correctamente.");
        } else {
            response.setEstadoPeticion("KO");
            response.setDescripcionPeticion("No se encontraron libros para el usuario con el estado especificado.");
        }}else {
			response.setEstadoPeticion("KO");
			response.setDescripcionPeticion("No se encontró al usuario.");
		}

        return response;
    }

    @Override
    public UserBookResponse moveUserBookToStatus(String usuarioNombre, String bookTitle, BookStatus status) {
        UserBookResponse response = new UserBookResponse();
		Optional<User> userOpt = userRepository.findByUsername(usuarioNombre);
		Book book = bookRepository.findByTitle(bookTitle);
		if(userOpt.isPresent() && book!=null) {
        Optional<UserBook> userBookOpt = userBookRepository.findByUsuarioIdAndBookId(userOpt.get().getId(), book.getId());
        
        if (userBookOpt.isPresent()) {
            UserBook userBook = userBookOpt.get();

            // Guardar la fecha de inicio si se cambia a READING
            if (status == BookStatus.READING) {
                userBook.setDateStarted(LocalDate.now());
            }

            // Guardar la fecha de lectura si se cambia a READED
            if (status == BookStatus.READED) {
                userBook.setDateRead(LocalDate.now());
            }

            // Actualizar el estado y guardar el UserBook
            userBook.setStatus(status);
            userBookRepository.save(userBook);

            // Construir UserBookRequest desde UserBook modificado
            UserBookRequest userBookRequest = userBookMapper.entityToDto(userBook);

            List<UserBookRequest> userBooks = new ArrayList<>();
            userBooks.add(userBookRequest);
            response.setListaUserBooks(userBooks);

            response.setEstadoPeticion("OK");
            response.setDescripcionPeticion("Libro movido a la lista de " + userBook.getStatus() + ".");
        } else {
            response.setDescripcionPeticion("El libro no está en la lista.");
            response.setEstadoPeticion("KO");
        }}else {
			response.setDescripcionPeticion("El usuario o el libro no existe.");
			response.setEstadoPeticion("KO");
        }

        return response;
    }

    @Override
    public UserBookResponse updateUserBookReview(String usuarioNombre, String bookTitle, String review) {
        UserBookResponse response = new UserBookResponse();
        String descripcionPeticion = "";
		Optional<User> userOpt = userRepository.findByUsername(usuarioNombre);
		Book book = bookRepository.findByTitle(bookTitle);
		if(userOpt.isPresent()&&book!=null) {
        descripcionPeticion = userBookService.updateUserBookReview(userOpt.get().getId(), book.getId(), review);
        response.setDescripcionPeticion(descripcionPeticion);
		}
        if (descripcionPeticion.contains("actualizada")) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }

    @Override
    public UserBookResponse updateUserBookRating(String usuarioNombre, String bookTitle, BigDecimal rating) {
        UserBookResponse response = new UserBookResponse();
        String descripcionPeticion = "";
		Optional<User> userOpt = userRepository.findByUsername(usuarioNombre);
		Book book = bookRepository.findByTitle(bookTitle);
		if(userOpt.isPresent()&&book!=null) {
        descripcionPeticion = userBookService.updateUserBookRating(userOpt.get().getId(), book.getId(), rating);
        response.setDescripcionPeticion(descripcionPeticion);
		}
        if (descripcionPeticion.contains("actualizada")) {
            response.setEstadoPeticion("OK");
        } else {
            response.setEstadoPeticion("KO");
        }
        return response;
    }
}
