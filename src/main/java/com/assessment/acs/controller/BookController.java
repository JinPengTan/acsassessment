package com.assessment.acs.controller;

import com.assessment.acs.modal.dto.AddBookDTO;
import com.assessment.acs.modal.dto.BookDTO;
import com.assessment.acs.services.BookServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Add a book or books into the system")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "400", description = "Please refer to the error message return by system")
    @ApiResponse(responseCode = "500", description = "Something unexpected error happen, please contact us")
    public ResponseEntity<Void> addBook(@RequestBody @Valid AddBookDTO request) {

        log.info("Addbook : Request {}", request.toString());

        bookServices.addBook(request.getIsbn(), request.getTitle(), request.getAuthor(), request.getNumberOfBooks());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    @Operation(summary = "Get list of books in pagination format")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "400", description = "Please refer to the error message return by system")
    @ApiResponse(responseCode = "500", description = "Something unexpected error happen, please contact us")
    public ResponseEntity<Page<BookDTO>> getBooks(Pageable pageable) {

        var books = bookServices.getBooks(pageable);

        return ResponseEntity.ok().body(books);
    }

}
