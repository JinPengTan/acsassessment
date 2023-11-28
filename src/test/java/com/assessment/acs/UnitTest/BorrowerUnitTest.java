package com.assessment.acs.UnitTest;

import com.assessment.acs.exception.DuplicateResourceException;
import com.assessment.acs.modal.entity.Borrower;
import com.assessment.acs.repository.BorrowerRepository;
import com.assessment.acs.services.impl.BorrowerServicesImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorrowerUnitTest {

	@Mock
	private BorrowerRepository borrowerRepository;

	@InjectMocks
	private BorrowerServicesImpl borrowerServices;

	@Test
	@DisplayName("Add borrower with new email")
	void addBorrower_NonDuplicateEmail_SaveAndFlush() {
		// Arrange
		String name = "John Doe";
		String email = "john.doe@example.com";

		// Mocking repository behavior to return an empty Optional, indicating a non-duplicate email
		when(borrowerRepository.findByEmail(email)).thenReturn(Optional.empty());

		// Act
		borrowerServices.addBorrower(name, email);

		// Assert
		verify(borrowerRepository, times(1)).saveAndFlush(any(Borrower.class));
	}

	@Test
	@DisplayName("Add borrower with existing email")
	void addBorrower_DuplicateEmail_DuplicateResourceException() {
		// Arrange
		String name = "John Doe";
		String email = "john.doe@example.com";

		// Mocking repository behavior to return a non-empty Optional, indicating a duplicate email
		when(borrowerRepository.findByEmail(email)).thenReturn(Optional.of(new Borrower()));

		// Act and Assert
		assertThrows(DuplicateResourceException.class, () -> {
			borrowerServices.addBorrower(name, email);
		});

		// Verify that saveAndFlush is never called
		verify(borrowerRepository, never()).saveAndFlush(any(Borrower.class));
	}

}
