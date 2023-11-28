package com.assessment.acs.modal.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookDTO {

    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String isbn;
    private String title;
    private String author;
    private Long borrowerId;
}
