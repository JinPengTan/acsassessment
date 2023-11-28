package com.assessment.acs.modal.entity;

import jakarta.persistence.*;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
@EqualsAndHashCode(callSuper = true)
public class Book extends BaseEntity {

    @Id
    @Column(name = "book_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "isbn", nullable = false, length = 50)
    private String isbn;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "author", nullable = false, length = 50)
    private String author;
    @ManyToOne
    @JoinColumn(name = "borrower_id")
    private Borrower borrower;

}
