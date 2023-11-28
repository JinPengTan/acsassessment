package com.assessment.acs.controller;

import com.assessment.acs.modal.dto.BorrowBookDTO;
import com.assessment.acs.modal.dto.ReturnBookDTO;
import com.assessment.acs.services.LibraryServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("library")
public class LibraryController {

    private final LibraryServices libraryServices;


    public LibraryController(LibraryServices libraryServices) {
        this.libraryServices = libraryServices;
    }

    @PutMapping("/borrow")
    @Operation(summary = "Borrow a book")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "400", description = "Please refer to the error message return by system")
    @ApiResponse(responseCode = "500", description = "Something unexpected error happen, please contact us")
    public ResponseEntity<Void> borrowBook(@RequestBody @Valid BorrowBookDTO request) {

        log.info("borrowBooks : borrower: {}, book ISBN: {}", request.getEmail(), request.getIsbn());

        libraryServices.borrowBooks(request.getEmail(), request.getIsbn());

        return ResponseEntity.ok().build();

    }

    @PutMapping("/return")
    @Operation(summary = "Return a book")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "400", description = "Please refer to the error message return by system")
    @ApiResponse(responseCode = "500", description = "Something unexpected error happen, please contact us")
    public ResponseEntity<Void> returBook(@RequestBody @Valid ReturnBookDTO request) {

        log.info("returnBooks : borrower: {}, book ISBN: {}", request.getEmail(), request.getIsbn());

        libraryServices.returnBooks(request.getEmail(), request.getIsbn());

        return ResponseEntity.ok().build();

    }

}
