package com.assessment.acs.APITest;

import com.assessment.acs.controller.LibraryController;
import com.assessment.acs.modal.dto.BorrowBookDTO;
import com.assessment.acs.modal.dto.ReturnBookDTO;
import com.assessment.acs.services.LibraryServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LibraryControllerTest {

    @Mock
    private LibraryServices libraryServices;

    @InjectMocks
    private LibraryController libraryController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(libraryController).build();
    }

    @Test
    @DisplayName("Borrow Book with Valid request")
    void borrowBook_ReturnsOkStatus() throws Exception {
        // Arrange
        BorrowBookDTO request = new BorrowBookDTO();
        request.setEmail("john.doe@example.com");
        request.setIsbn("123456789");

        // Mock the behavior of libraryServices.borrowBooks method
        doNothing().when(libraryServices).borrowBooks(request.getEmail(), request.getIsbn());

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/library/borrow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"john.doe@example.com\",\"isbn\":\"123456789\"}")
                )
                .andExpect(status().isOk());

        // Verify that libraryServices.borrowBooks is called once with the correct arguments
        verify(libraryServices).borrowBooks(request.getEmail(), request.getIsbn());
    }

    @ParameterizedTest
    @CsvSource({
            "{\"email\":\"john.doe@example.com\",\"isbn\":\"null\"}",
            "{\"email\":\"john.doe@example.com\"}",
            "{\"email\":\"null\",\"isbn\":\"1234567\"}",
            "{\"isbn\":\"1234567\"}",
            "{\"email\":\"invalidEmail\",\"isbn\":\"1234567\"}",
    })
    @DisplayName("Borrow book with Invalid Request")
    void borrowBook_withInvalidRequest(String requestBody) throws Exception {
        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/library/borrow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isBadRequest());

    }


    @Test
    @DisplayName("Return Book with Valid request")
    void returnBook_ReturnsOkStatus() throws Exception {
        // Arrange
        ReturnBookDTO request = new ReturnBookDTO();
        request.setEmail("john.doe@example.com");
        request.setIsbn("123456789");

        // Mock the behavior of libraryServices.returnBooks method
        doNothing().when(libraryServices).returnBooks(request.getEmail(), request.getIsbn());

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/library/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"john.doe@example.com\",\"isbn\":\"123456789\"}")
                )
                .andExpect(status().isOk());

        // Verify that libraryServices.returnBooks is called once with the correct arguments
        verify(libraryServices).returnBooks(request.getEmail(), request.getIsbn());
    }

    @ParameterizedTest
    @CsvSource({
            "{\"email\":\"john.doe@example.com\",\"isbn\":\"null\"}",
            "{\"email\":\"john.doe@example.com\"}",
            "{\"email\":\"null\",\"isbn\":\"1234567\"}",
            "{\"isbn\":\"1234567\"}",
            "{\"email\":\"invalidEmail\",\"isbn\":\"1234567\"}",
    })
    @DisplayName("Return book with Invalid Request")
    void returnBook_withInvalidRequest(String requestBody) throws Exception {
        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/library/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isBadRequest());

    }
}
