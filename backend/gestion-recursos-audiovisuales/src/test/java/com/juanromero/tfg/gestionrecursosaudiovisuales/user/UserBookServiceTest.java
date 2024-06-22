package com.juanromero.tfg.gestionrecursosaudiovisuales.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.book.Book;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.BookStatus;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.User;
import com.juanromero.tfg.gestionrecursosaudiovisuales.entity.user.UserBook;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserBookRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.repository.user.UserRepository;
import com.juanromero.tfg.gestionrecursosaudiovisuales.service.user.impl.UserBookServiceImpl;

public class UserBookServiceTest {

	@InjectMocks
	private UserBookServiceImpl userBookService;

	@Mock
	private UserBookRepository userBookRepository;

	@Mock
	private UserRepository userRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	private UserBook createUserBook() {
		User user = new User();
		user.setId(1);
		user.setNombre("Test User");
		List<UserBook> userBooks = new ArrayList<>();
		user.setUserBooks(userBooks);

		Book book = new Book();
		book.setId(1);
		book.setTitle("Test Book");

		UserBook userBook = new UserBook();
		userBook.setId(1);
		userBook.setUsuario(user);
		userBook.setBook(book);
		userBook.setStatus(BookStatus.PENDING);

		return userBook;
	}

	@Test
	void testAddUserBook() {
		UserBook userBook = createUserBook();
		User user = userBook.getUsuario();

		when(userBookRepository.findByUsuarioIdAndBookId(user.getId(), userBook.getBook().getId()))
				.thenReturn(Optional.empty());
		when(userRepository.save(any(User.class))).thenReturn(user);

		String result = userBookService.addUserBook(userBook);

		assertEquals("Libro añadido a la lista del usuario.", result);
		verify(userBookRepository, times(1)).save(userBook);
		verify(userRepository, times(1)).save(user);
	}

	@Test
	void testAddUserBookAlreadyExists() {
		UserBook userBook = createUserBook();
		User user = userBook.getUsuario();

		when(userBookRepository.findByUsuarioIdAndBookId(user.getId(), userBook.getBook().getId()))
				.thenReturn(Optional.of(userBook));

		String result = userBookService.addUserBook(userBook);

		assertEquals("El libro ya está en la lista del usuario.", result);
		verify(userBookRepository, times(0)).save(userBook);
		verify(userRepository, times(0)).save(user);
	}

	@Test
	void testDeleteUserBook() {
		UserBook userBook = createUserBook();

		when(userBookRepository.findByUsuarioIdAndBookId(userBook.getUsuario().getId(), userBook.getBook().getId()))
				.thenReturn(Optional.of(userBook));

		String result = userBookService.deleteUserBook(userBook.getUsuario().getId(), userBook.getBook().getId());

		assertEquals("Libro eliminado de la lista del usuario.", result);
		verify(userBookRepository, times(1)).delete(userBook);
		verify(userRepository, times(1)).save(userBook.getUsuario());
	}

	@Test
	void testDeleteUserBookNotFound() {
		UserBook userBook = createUserBook();

		when(userBookRepository.findByUsuarioIdAndBookId(userBook.getUsuario().getId(), userBook.getBook().getId()))
				.thenReturn(Optional.empty());

		String result = userBookService.deleteUserBook(userBook.getUsuario().getId(), userBook.getBook().getId());

		assertEquals("El libro no está en la lista del usuario.", result);
		verify(userBookRepository, times(0)).delete(userBook);
		verify(userRepository, times(0)).save(userBook.getUsuario());
	}

	@Test
	void testFindAllUserBooks() {
		UserBook userBook = createUserBook();
		List<UserBook> userBooks = new ArrayList<>();
		userBooks.add(userBook);

		when(userBookRepository.findByUsuarioId(userBook.getUsuario().getId())).thenReturn(userBooks);

		List<UserBook> result = userBookService.findAllUserBooks(userBook.getUsuario().getId());

		assertEquals(userBooks.size(), result.size());
		assertEquals(userBooks.get(0), result.get(0));
	}

	@Test
	void testFindUserBooksByStatus() {
		UserBook userBook = createUserBook();
		List<UserBook> userBooks = new ArrayList<>();
		userBooks.add(userBook);

		when(userBookRepository.findByUsuarioIdAndStatus(userBook.getUsuario().getId(), BookStatus.READING))
				.thenReturn(userBooks);

		List<UserBook> result = userBookService.findUserBooksByStatus(userBook.getUsuario().getId(),
				BookStatus.READING);

		assertEquals(userBooks.size(), result.size());
		assertEquals(userBooks.get(0), result.get(0));
	}

	@Test
	void testMoveUserBookToStatus() {
		UserBook userBook = createUserBook();

		when(userBookRepository.findByUsuarioIdAndBookId(userBook.getUsuario().getId(), userBook.getBook().getId()))
				.thenReturn(Optional.of(userBook));
		when(userBookRepository.save(any(UserBook.class))).thenReturn(userBook);

		String result = userBookService.moveUserBookToStatus(userBook.getUsuario().getId(), userBook.getBook().getId(),
				BookStatus.READING);

		assertEquals("Libro movido a READING.", result);
		assertEquals(BookStatus.READING, userBook.getStatus());
		assertNotNull(userBook.getDateStarted());
	}

	@Test
	void testMoveUserBookToStatusBookNotFound() {
		UserBook userBook = createUserBook();

		when(userBookRepository.findByUsuarioIdAndBookId(userBook.getUsuario().getId(), userBook.getBook().getId()))
				.thenReturn(Optional.empty());

		String result = userBookService.moveUserBookToStatus(userBook.getUsuario().getId(), userBook.getBook().getId(),
				BookStatus.READING);

		assertEquals("El libro no está en la lista.", result);
	}

	@Test
	void testUpdateUserBookReview() {
		UserBook userBook = createUserBook();

		when(userBookRepository.findByUsuarioIdAndBookId(userBook.getUsuario().getId(), userBook.getBook().getId()))
				.thenReturn(Optional.of(userBook));
		when(userBookRepository.save(any(UserBook.class))).thenReturn(userBook);

		String result = userBookService.updateUserBookReview(userBook.getUsuario().getId(), userBook.getBook().getId(),
				"Updated review");

		assertEquals("Review del libro actualizada.", result);
		assertEquals("Updated review", userBook.getReview());
	}

	@Test
	void testUpdateUserBookReviewBookNotFound() {
		UserBook userBook = createUserBook();

		when(userBookRepository.findByUsuarioIdAndBookId(userBook.getUsuario().getId(), userBook.getBook().getId()))
				.thenReturn(Optional.empty());

		String result = userBookService.updateUserBookReview(userBook.getUsuario().getId(), userBook.getBook().getId(),
				"Updated review");

		assertEquals("El libro no está en la lista.", result);
	}

	@Test
	void testUpdateUserBookRating() {
		UserBook userBook = createUserBook();

		when(userBookRepository.findByUsuarioIdAndBookId(userBook.getUsuario().getId(), userBook.getBook().getId()))
				.thenReturn(Optional.of(userBook));
		when(userBookRepository.save(any(UserBook.class))).thenReturn(userBook);

		String result = userBookService.updateUserBookRating(userBook.getUsuario().getId(), userBook.getBook().getId(),
				new BigDecimal("4.5"));

		assertEquals("Rating del libro actualizado.", result);
		assertEquals(new BigDecimal("4.5"), userBook.getRating());
	}

	@Test
	void testUpdateUserBookRatingBookNotFound() {
		UserBook userBook = createUserBook();

		when(userBookRepository.findByUsuarioIdAndBookId(userBook.getUsuario().getId(), userBook.getBook().getId()))
				.thenReturn(Optional.empty());

		String result = userBookService.updateUserBookRating(userBook.getUsuario().getId(), userBook.getBook().getId(),
				new BigDecimal("4.5"));

		assertEquals("El libro no está en la lista.", result);
	}

}
