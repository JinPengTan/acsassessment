package com.assessment.acs.services.impl;

import com.assessment.acs.exception.ResourceNotFoundException;
import com.assessment.acs.repository.BookRepository;
import com.assessment.acs.repository.BorrowerRepository;
import com.assessment.acs.services.LibraryServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("LibraryServices")
public class LibraryServicesImpl implements LibraryServices {

    private final BorrowerRepository borrowerRepository;
    private final BookRepository bookRepository;

    public LibraryServicesImpl(BorrowerRepository borrowerRepository, BookRepository bookRepository) {
        this.borrowerRepository = borrowerRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void borrowBooks(String email, String isbn) {

        //Get the book with isbn
        var book = bookRepository.findFirstByIsbnAndBorrowerIsNull(isbn).orElseThrow(() ->
                new ResourceNotFoundException("Book Not available to borrow"));
        //Get the borrower
        var borrower = borrowerRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Unable to find borrower's email"));

        book.setBorrower(borrower);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnBooks(String email, String isbn) {

        //Get the borrower details
        var borrower = borrowerRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Unable to find borrower's email"));
        //Get the return book details
        var book = bookRepository.findFirstByIsbnAndBorrower(isbn, borrower).orElseThrow(() ->
                new ResourceNotFoundException("Borrower didn't borrow this book"));
        //Unbound the relationship
        book.setBorrower(null);
    }

}
