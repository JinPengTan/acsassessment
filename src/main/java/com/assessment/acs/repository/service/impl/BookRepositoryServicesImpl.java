package com.assessment.acs.repository.service.impl;

import com.assessment.acs.modal.entity.Book;
import com.assessment.acs.repository.BookRepository;
import com.assessment.acs.repository.service.BookRepositoryServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("BookRepositoryServices")
@Slf4j
public class BookRepositoryServicesImpl implements BookRepositoryServices {

    private final BookRepository bookRepository;

    @Autowired
    public BookRepositoryServicesImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Cacheable(value = "bookInfo", key = "#isbn")
    public Optional<Book> getFirstBookByIsbn(String isbn) {
        return bookRepository.findFirstByIsbn(isbn);
    }

    public void saveAllBooks(List<Book> books) {
        bookRepository.saveAll(books);
    }

    public Page<Book> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

}
