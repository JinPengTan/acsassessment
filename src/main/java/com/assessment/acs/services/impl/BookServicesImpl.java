package com.assessment.acs.services.impl;

import com.assessment.acs.exception.ValidationException;
import com.assessment.acs.modal.dto.BookDTO;
import com.assessment.acs.modal.entity.Book;
import com.assessment.acs.modal.mapper.BookMapper;
import com.assessment.acs.repository.service.BookRepositoryServices;
import com.assessment.acs.services.BookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("BookServices")
public class BookServicesImpl implements BookServices {

    private final BookRepositoryServices bookRepository;
    private final BookMapper mapper;

    @Autowired
    public BookServicesImpl(BookRepositoryServices bookRepository, BookMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    @Override
    public void addBook(String isbn, String title, String author, int numberOfBooks) {

        //Validation before we proceed to insert the boooks
        if(Boolean.FALSE.equals(isbnValidation(isbn, author, title))) {
            throw new ValidationException("Books with same ISBN numbers must have the same title and same author");
        }

        List<Book> books = new ArrayList<>(numberOfBooks);
        //Let's assume Librarian can add more than 1 books with one create request
        //Scenario: Librarian insert the book information, then allow to add N books with same isbn, title, author
        for (int i = 0; i < numberOfBooks; i++) {
            Book book = Book.builder()
                    .author(author)
                    .title(title)
                    .isbn(isbn)
                    .build();
            books.add(book);
        }

        //Save all the books
        bookRepository.saveAllBooks(books);
    }

    @Override
    public Page<BookDTO> getBooks(Pageable pageable) {

        return mapper.booksToDTOPage(bookRepository.getBooks(pageable));


    }

    private Boolean isbnValidation(String isbn, String author, String title) {

        Optional<Book> optionalBook = bookRepository.getFirstBookByIsbn(isbn);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();

            //If same ISBN numbers must have the same title and same author
            return book.getAuthor().equals(author) && book.getTitle().equals(title);
        }

        return true;
    }
}
