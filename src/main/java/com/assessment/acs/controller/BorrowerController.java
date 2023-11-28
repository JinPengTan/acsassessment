package com.assessment.acs.controller;

import com.assessment.acs.modal.dto.CreateBorrowerDTO;
import com.assessment.acs.services.BorrowerServices;
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
    public ResponseEntity<Void> createBorrower(@RequestBody @Valid CreateBorrowerDTO request) {

        borrowerServices.addBorrower(request.getName(), request.getEmail());

        return ResponseEntity.ok().build();
    }
}
