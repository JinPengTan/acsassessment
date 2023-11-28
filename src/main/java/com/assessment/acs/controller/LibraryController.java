package com.assessment.acs.controller;

import com.assessment.acs.modal.dto.BorrowBookDTO;
import com.assessment.acs.modal.dto.ReturnBookDTO;
import com.assessment.acs.services.LibraryServices;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("library")
public class LibraryController {

    private final LibraryServices libraryServices;


    public LibraryController(LibraryServices libraryServices) {
        this.libraryServices = libraryServices;
    }

    @PutMapping("/borrow")
    public ResponseEntity<Void> borrowBook(@RequestBody @Valid BorrowBookDTO request) {

        libraryServices.borrowBooks(request.getEmail(), request.getIsbn());

        return ResponseEntity.ok().build();

    }

    @PutMapping("/return")
    public ResponseEntity<Void> borrowBook(@RequestBody @Valid ReturnBookDTO request) {

        libraryServices.returnBooks(request.getEmail(), request.getIsbn());

        return ResponseEntity.ok().build();

    }

}
