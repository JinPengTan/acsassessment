package com.assessment.acs.modal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BorrowBookDTO {

    @NotNull(message = "Borrower Name cannot be null")
    @Email(message = "Invalid email format")
    private String email;
    @NotNull(message = "Book isbn cannot be null")
    private String isbn;

}
