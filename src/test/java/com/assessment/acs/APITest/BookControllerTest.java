package com.assessment.acs.APITest;

import com.assessment.acs.controller.BookController;
import com.assessment.acs.modal.dto.AddBookDTO;
import com.assessment.acs.modal.dto.BookDTO;
import com.assessment.acs.services.BookServices;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookServices bookServices;

    @InjectMocks
    private BookController bookController;

    @Test
    @Order(1)
    @DisplayName("AddBook with Valid request with numberOfBook parameter")
    void addBook_ValidRequest_ReturnsOk() throws Exception {
        // Arrange
        AddBookDTO request = new AddBookDTO();
        request.setIsbn("123456789");
        request.setTitle("Mock Book");
        request.setAuthor("Mock Book");
        request.setNumberOfBooks(1);

        // Mock the behavior of the bookServices.addBook method
        doNothing().when(bookServices).addBook(request.getIsbn(), request.getTitle(), request.getAuthor(), request.getNumberOfBooks());

        // Set up the MockMvc instance
        MockMvc mockMvc = standaloneSetup(bookController).build();

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/book/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"isbn\":\"123456789\",\"title\":\"Mock Book\",\"author\":\"Mock Book\",\"numberOfBooks\":1}")
                )
                .andExpect(status().isOk());

        // Verify that bookServices.addBook is called once with the correct arguments
        verify(bookServices).addBook(request.getIsbn(), request.getTitle(), request.getAuthor(), request.getNumberOfBooks());
    }

    @Test
    @Order(2)
    @DisplayName("AddBook with Valid request without numberOfBooks parameter")
    void addBook_ValidRequest_WithNoNumberOfBooks_ReturnsOk() throws Exception {
        // Arrange
        AddBookDTO request = new AddBookDTO();
        request.setIsbn("123456789");
        request.setTitle("Mock Book");
        request.setAuthor("Mock Book");
        request.setNumberOfBooks(1);

        // Mock the behavior of the bookServices.addBook method
        doNothing().when(bookServices).addBook(request.getIsbn(), request.getTitle(), request.getAuthor(), request.getNumberOfBooks());

        // Set up the MockMvc instance
        MockMvc mockMvc = standaloneSetup(bookController).build();

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/book/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"isbn\":\"123456789\",\"title\":\"Mock Book\",\"author\":\"Mock Book\"}")
                )
                .andExpect(status().isOk());

        // Verify that bookServices.addBook is called once with the correct arguments
        verify(bookServices).addBook(request.getIsbn(), request.getTitle(), request.getAuthor(), request.getNumberOfBooks());
    }

    @ParameterizedTest
    @CsvSource({
            "{\"isbn\":\"123456789\",\"title\":\"Mock Book\",\"author\":\"Mock Book\",\"numberOfBooks\":-1}",
            "{\"isbn\":\"123456789\",\"title\":\"Mock Book\",\"author\":\"Mock Book\",\"numberOfBooks\":0}",
            "{\"title\":\"Mock Book\",\"author\":\"Mock Book\",\"numberOfBooks\":1}",
            "{\"isbn\":\"123456789\",\"author\":\"Mock Book\",\"numberOfBooks\":1}",
            "{\"isbn\":\"123456789\",\"title\":\"Mock Book\",\"numberOfBooks\":1}"
    })
    @Order(3)
    @DisplayName("AddBook with Invalid request")
    void addBook_InvalidRequest_WithNegativeNumberOfBooks_ReturnsBadRequest(String requestBody) throws Exception {
        // Set up the MockMvc instance
        MockMvc mockMvc = standaloneSetup(bookController).build();

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/book/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isBadRequest());

    }

}

