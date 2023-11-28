package com.assessment.acs.repository;

import com.assessment.acs.modal.entity.Book;
import com.assessment.acs.modal.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findFirstByIsbn(String isbn);

    Optional<Book> findFirstByIsbnAndBorrowerIsNull(String isbn);

    Optional<Book> findFirstByIsbnAndBorrower(String isbn, Borrower borrower);

}
