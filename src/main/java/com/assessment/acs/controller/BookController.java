package com.assessment.acs.controller;

import com.assessment.acs.modal.dto.AddBookDTO;
import com.assessment.acs.modal.dto.BookDTO;
import com.assessment.acs.services.BookServices;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/book")
public class BookController {


    private final BookServices bookServices;


    public BookController(BookServices bookServices) {
        this.bookServices = bookServices;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> addBook(@RequestBody @Valid AddBookDTO request) {

        bookServices.addBook(request.getIsbn(), request.getTitle(), request.getAuthor(), request.getNumberOfBooks());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<Page<BookDTO>> getBooks(Pageable pageable) {

        var books = bookServices.getBooks(pageable);

        return ResponseEntity.ok().body(books);
    }

}
