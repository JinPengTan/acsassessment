package com.assessment.acs.UnitTest;

import com.assessment.acs.exception.ResourceNotFoundException;
import com.assessment.acs.modal.entity.Book;
import com.assessment.acs.modal.entity.Borrower;
import com.assessment.acs.repository.BookRepository;
import com.assessment.acs.repository.BorrowerRepository;
import com.assessment.acs.services.impl.LibraryServicesImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibraryUnitTest {

	@Mock
	private BorrowerRepository borrowerRepository;

	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private LibraryServicesImpl libraryServices;

	@Test
	@Order(1)
	@DisplayName("Borrow book with valid borrower and book")
	void borrowBooks_ValidBorrowerAndBook_BorrowerAssigned() {
		// Arrange
		String email = "john.doe@example.com";
		String isbn = "123456789";
		String john = "John";

		Borrower borrower = Borrower.builder()
				.name(john)
				.email(email)
				.build();

		Book book = Book.builder()
				.isbn(isbn)
				.title("Mock Book")
				.author("Mock Book")
				.build();

		// Mocking repository behavior to return a borrower
		when(borrowerRepository.findByEmail(email)).thenReturn(Optional.of(borrower));

		// Mocking repository behavior to return a book
		when(bookRepository.findFirstByIsbnAndBorrowerIsNull(isbn)).thenReturn(Optional.of(book));

		// Act
		libraryServices.borrowBooks(email, isbn);

		book.setBorrower(borrower);

		// Assert
		verify(bookRepository, times(1)).findFirstByIsbnAndBorrowerIsNull(isbn);
		verify(borrowerRepository, times(1)).findByEmail(email);

	}

	@Test
	@Order(2)
	@DisplayName("Borrow book with invalid book")
	void borrowBooks_InvalidBook_ResourceNotFoundException() {
		// Arrange
		String email = "john.doe@example.com";
		String isbn = "123456789";

		when(bookRepository.findFirstByIsbnAndBorrowerIsNull(isbn)).thenReturn(Optional.empty());
		// Act and Assert
		assertThrows(ResourceNotFoundException.class, () -> {
			libraryServices.borrowBooks(email, isbn);
		});

		// Verify that findFirstByIsbnAndBorrowerIsNull is never called
		verify(bookRepository, times(1)).findFirstByIsbnAndBorrowerIsNull(anyString());
	}

	@Test
	@Order(3)
	@DisplayName("Borrow book with Invalid borrower")
	void borrowBooks_InvalidBorrower_ResourceNotFoundException() {
		// Arrange
		String email = "john.doe@example.com";
		String isbn = "123456789";

		when(bookRepository.findFirstByIsbnAndBorrowerIsNull(isbn)).thenReturn(Optional.of(new Book()));
		// Mocking repository behavior to return no borrower
		when(borrowerRepository.findByEmail(email)).thenReturn(Optional.empty());

		// Act and Assert
		assertThrows(ResourceNotFoundException.class, () -> {
			libraryServices.borrowBooks(email, isbn);
		});

		// Verify that findFirstByIsbnAndBorrowerIsNull is never called
		verify(bookRepository, times(1)).findFirstByIsbnAndBorrowerIsNull(anyString());
	}


	@Test
	@Order(4)
	@DisplayName("Return book with valid borrower and book")
	void returnBooks_ValidBorrowerAndBook_BorrowerUnassigned() {
		// Arrange
		String email = "john.doe@example.com";
		String isbn = "123456789";

		// Mocking repository behavior to return a borrower
		when(borrowerRepository.findByEmail(email)).thenReturn(Optional.of(new Borrower()));

		// Mocking repository behavior to return a book
		when(bookRepository.findFirstByIsbnAndBorrower(anyString(), any(Borrower.class)))
				.thenReturn(Optional.of(new Book()));

		// Act
		libraryServices.returnBooks(email, isbn);

		// Assert
		verify(bookRepository, times(1)).findFirstByIsbnAndBorrower(anyString(), any(Borrower.class));
		verify(borrowerRepository, times(1)).findByEmail(email);
	}

	@Test
	@Order(5)
	@DisplayName("Return book with Invalid borrower")
	void returnBooks_InvalidBorrower_ResourceNotFoundException() {
		// Arrange
		String email = "john.doe@example.com";
		String isbn = "123456789";

		// Mocking repository behavior to return no borrower
		when(borrowerRepository.findByEmail(email)).thenReturn(Optional.empty());

		// Act and Assert
		assertThrows(ResourceNotFoundException.class, () -> {
			libraryServices.returnBooks(email, isbn);
		});

		// Verify that findFirstByIsbnAndBorrower is never called
		verify(bookRepository, never()).findFirstByIsbnAndBorrower(anyString(), any(Borrower.class));
	}

	@Test
	@Order(6)
	@DisplayName("Return book with Invalid book")
	void returnBooks_InvalidBook_ResourceNotFoundException() {
		// Arrange
		String email = "john.doe@example.com";
		String isbn = "123456789";

		// Mocking repository behavior to return a borrower
		when(borrowerRepository.findByEmail(email)).thenReturn(Optional.of(new Borrower()));

		// Mocking repository behavior to return a book
		when(bookRepository.findFirstByIsbnAndBorrower(anyString(), any(Borrower.class)))
				.thenReturn(Optional.empty());

		// Act and Assert
		assertThrows(ResourceNotFoundException.class, () -> {
			libraryServices.returnBooks(email, isbn);
		});

		// Assert
		verify(bookRepository, times(1)).findFirstByIsbnAndBorrower(anyString(), any(Borrower.class));
		verify(borrowerRepository, times(1)).findByEmail(email);
	}



}
