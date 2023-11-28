package com.assessment.acs.controller;

import com.assessment.acs.modal.dto.BorrowBookDTO;
import com.assessment.acs.modal.dto.ReturnBookDTO;
import com.assessment.acs.services.LibraryServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Borrow a book")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "400", description = "Please refer to the error message return by system")
    @ApiResponse(responseCode = "500", description = "Something unexpected error happen, please contact us")
    public ResponseEntity<Void> borrowBook(@RequestBody @Valid BorrowBookDTO request) {

        libraryServices.borrowBooks(request.getEmail(), request.getIsbn());

        return ResponseEntity.ok().build();

    }

    @PutMapping("/return")
    @Operation(summary = "Return a book")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "400", description = "Please refer to the error message return by system")
    @ApiResponse(responseCode = "500", description = "Something unexpected error happen, please contact us")
    public ResponseEntity<Void> returBook(@RequestBody @Valid ReturnBookDTO request) {

        libraryServices.returnBooks(request.getEmail(), request.getIsbn());

        return ResponseEntity.ok().build();

    }

}
