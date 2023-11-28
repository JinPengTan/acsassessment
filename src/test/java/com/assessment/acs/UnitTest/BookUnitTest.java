package com.assessment.acs.UnitTest;

import com.assessment.acs.exception.ValidationException;
import com.assessment.acs.modal.dto.BookDTO;
import com.assessment.acs.modal.entity.Book;
import com.assessment.acs.modal.mapper.BookMapper;
import com.assessment.acs.repository.service.BookRepositoryServices;
import com.assessment.acs.services.impl.BookServicesImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookUnitTest {

	@Mock
	private BookRepositoryServices bookRepository;

	@Mock
	private BookMapper mapper;

	@InjectMocks
	private BookServicesImpl bookServices;

	@Test
	@DisplayName("Add book with same isbn, same title and same author")
	void addBook_ValidIsbnRulesInput_SaveAllBooks() {
		// Arrange
		String isbn = "123456789";
		String title = "Test Book";
		String author = "Test Author";

		Book book = Book.builder()
				.isbn(isbn)
				.title(title)
				.author(author)
				.build();

		int numberOfBooks = 3;

		// Mocking isbnValidation to return true
		when(bookRepository.getFirstBookByIsbn(isbn)).thenReturn(Optional.of(book));

		// Act
		bookServices.addBook(isbn, title, author, numberOfBooks);

		// Assert
		verify(bookRepository, times(1)).saveAllBooks(anyList());
	}

	@Test
	@DisplayName("Add book with different isbn but same title and same author")
	void addBook_ValidTitleAndAuthorInput_ValidationException() {
		// Arrange
		String isbn = "123456789";
		String title = "Test Book";
		String author = "Test Author";
		int numberOfBooks = 3;

		Book book = Book.builder()
				.isbn("0001")
				.title(title)
				.author(author)
				.build();

		// Mocking isbnValidation to return false
		when(bookRepository.getFirstBookByIsbn(isbn)).thenReturn(Optional.of(book));

		// Act and Assert
		bookServices.addBook(isbn, title, author, numberOfBooks);


		// Verify that saveBook is never called
		verify(bookRepository, times(1)).saveAllBooks(anyList());
	}

	@Test
	@DisplayName("Add book with same isbn, different title but same author")
	void addBook_InvalidTitleInput_ValidationException() {
		// Arrange
		String isbn = "123456789";
		String title = "Test Book";
		String author = "Test Author";
		int numberOfBooks = 3;

		Book book = Book.builder()
				.isbn(isbn)
				.title("Mock Book")
				.author(title)
				.build();

		// Mocking isbnValidation to return false
		when(bookRepository.getFirstBookByIsbn(isbn)).thenReturn(Optional.of(book));

		// Act and Assert
		assertThrows(ValidationException.class, () -> {
			bookServices.addBook(isbn, title, author, numberOfBooks);
		});

		// Verify that saveBook is never called
		verify(bookRepository, never()).saveAllBooks(anyList());
	}

	@Test
	@DisplayName("Add book with same isbn, same title but different author")
	void addBook_InvalidAuthorInput_ValidationException() {
		// Arrange
		String isbn = "123456789";
		String title = "Test Book";
		String author = "Test Author";
		int numberOfBooks = 3;

		Book book = Book.builder()
				.isbn(isbn)
				.title(title)
				.author("Mock Book")
				.build();

		// Mocking isbnValidation to return false
		when(bookRepository.getFirstBookByIsbn(isbn)).thenReturn(Optional.of(book));

		// Act and Assert
		assertThrows(ValidationException.class, () -> {
			bookServices.addBook(isbn, title, author, numberOfBooks);
		});

		// Verify that saveBook is never called
		verify(bookRepository, never()).saveAllBooks(anyList());
	}

	@Test
	@DisplayName("Add book with same isbn, but different title and author")
	void addBook_InvalidTitleAndAuthorInput_ValidationException() {
		// Arrange
		String isbn = "123456789";
		String title = "Test Book";
		String author = "Test Author";
		int numberOfBooks = 3;

		Book book = Book.builder()
				.isbn(isbn)
				.title(title)
				.author("Mock Book")
				.build();

		// Mocking isbnValidation to return false
		when(bookRepository.getFirstBookByIsbn(isbn)).thenReturn(Optional.of(book));

		// Act and Assert
		assertThrows(ValidationException.class, () -> {
			bookServices.addBook(isbn, title, author, numberOfBooks);
		});

		// Verify that saveBook is never called
		verify(bookRepository, never()).saveAllBooks(anyList());
	}


	@Test
	@DisplayName("Return Pageable bookDTOs with records")
	void getBooks_ReturnsPageOfBookDTOs() {
		// Arrange
		Pageable pageable = mock(Pageable.class);

		// Mocking data from the repository
		List<Book> bookList = createMockBookList();
		Page<Book> bookPage = new PageImpl<>(bookList);
		when(bookRepository.getBooks(pageable)).thenReturn(bookPage);

		// Mocking the mapping process
		List<BookDTO> bookDTOList = createMockBookDTOList();
		when(mapper.booksToDTOPage(bookPage)).thenReturn(new PageImpl<>(bookDTOList));

		// Act
		Page<BookDTO> result = bookServices.getBooks(pageable);

		// Assert
		assertEquals(bookDTOList, result.getContent());
	}

	@Test
	@DisplayName("Return Pageable bookDTOs with empty record")
	void getBooks_ReturnPageOfEmptyBookDTOs() {
		// Arrange
		Pageable pageable = mock(Pageable.class);

		// Mocking data from the repository
		Page<Book> bookPage = new PageImpl<>(Collections.emptyList());
		when(bookRepository.getBooks(pageable)).thenReturn(bookPage);

		// Mocking the mapping process
		List<BookDTO> bookDTOList = Collections.emptyList();
		when(mapper.booksToDTOPage(bookPage)).thenReturn(new PageImpl<>(Collections.emptyList()));

		// Act
		Page<BookDTO> result = bookServices.getBooks(pageable);

		// Assert
		assertEquals(bookDTOList, result.getContent());
	}

	private List<Book> createMockBookList() {

		Book book = Book.builder()
				.id(1L)
				.isbn("Mock Book")
				.author("Mock Book")
				.title("Mock Book")
				.build();
		// Create and return a list of mock Book entities
		// You can customize this based on your actual requirements
		return List.of(book); // Replace with actual book entities
	}

	private List<BookDTO> createMockBookDTOList() {
		BookDTO book = new BookDTO();
		book.setId(1L);
		book.setIsbn("Mock Book");
		book.setAuthor("Mock Book");
		book.setTitle("Mock Book");
		book.setCreatedDate(LocalDateTime.now());
		book.setUpdatedDate(LocalDateTime.now());
		// Create and return a list of mock BookDTOs
		// You can customize this based on your actual requirements
		return List.of(book); // Replace with actual BookDTOs
	}

}
