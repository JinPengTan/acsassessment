package com.assessment.acs.modal.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddBookDTO {

    @NotNull(message = "Isbn of book cannot be null")
    private String isbn;
    @NotNull(message = "Title of book cannot be null")
    private String title;
    @NotNull(message = "Author of book cannot be null")
    private String author;
    @Min(value = 1, message = "numberOfBook must be more than 1")
    private int numberOfBooks = 1;

}
