package com.assessment.acs.repository.service;

import com.assessment.acs.modal.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryServices {

    Optional<Book> getFirstBookByIsbn(String isbn);

    void saveAllBooks(List<Book> books);

    Page<Book> getBooks(Pageable pageable);

}
