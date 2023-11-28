package com.assessment.acs.services;

import com.assessment.acs.modal.dto.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BookServices {

    void addBook(String isbn, String title, String author, int numberOfBooks);

    Page<BookDTO> getBooks(Pageable pageable);



}
