package com.assessment.acs.modal.mapper;

import com.assessment.acs.modal.dto.BookDTO;
import com.assessment.acs.modal.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {


    @Mapping(target = "borrowerId", expression = "java(book.getBorrower() == null ? null : book.getBorrower().getId())")
    BookDTO bookToDTO(Book book);

    List<BookDTO> booksToDTO(List<Book> books);

    default Page<BookDTO> booksToDTOPage(Page<Book> books) {
        return new PageImpl<>(booksToDTO(books.getContent()), books.getPageable(), books.getTotalElements());
    }
}
