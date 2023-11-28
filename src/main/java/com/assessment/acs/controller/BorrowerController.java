package com.assessment.acs.controller;

import com.assessment.acs.modal.dto.CreateBorrowerDTO;
import com.assessment.acs.services.BorrowerServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/borrower")
public class BorrowerController {

    private final BorrowerServices borrowerServices;

    public BorrowerController(BorrowerServices borrowerServices) {
        this.borrowerServices = borrowerServices;
    }

    @PostMapping("/create")
    @Operation(summary = "Create a borrower into the system")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "400", description = "Please refer to the error message return by system")
    @ApiResponse(responseCode = "500", description = "Something unexpected error happen, please contact us")
    public ResponseEntity<Void> createBorrower(@RequestBody @Valid CreateBorrowerDTO request) {

        borrowerServices.addBorrower(request.getName(), request.getEmail());

        return ResponseEntity.ok().build();
    }
}
