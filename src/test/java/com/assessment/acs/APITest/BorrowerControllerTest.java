package com.assessment.acs.APITest;

import com.assessment.acs.controller.BorrowerController;
import com.assessment.acs.modal.dto.CreateBorrowerDTO;
import com.assessment.acs.services.BorrowerServices;
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
class BorrowerControllerTest {

    @Mock
    private BorrowerServices borrowerServices;

    @InjectMocks
    private BorrowerController borrowerController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(borrowerController).build();
    }

    @Test
    @DisplayName("Create Borrower with valid Request")
    void createBorrower_ValidRequest_ReturnsOk() throws Exception {
        // Arrange
        CreateBorrowerDTO request = new CreateBorrowerDTO();
        request.setName("John Doe");
        request.setEmail("john.doe@example.com");

        // Mock the behavior of borrowerServices.addBorrower method
        doNothing().when(borrowerServices).addBorrower(request.getName(), request.getEmail());

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/borrower/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\"}")
                )
                .andExpect(status().isOk());

        // Verify that borrowerServices.addBorrower is called once with the correct arguments
        verify(borrowerServices).addBorrower(request.getName(), request.getEmail());
    }

    @ParameterizedTest
    @CsvSource({
            "{\"name\":null,\"email\":\"john.doe@example.com\"}",
            "{\"name\":\"John\",\"email\":\"null\"}",
            "{\"name\":\"John\"}",
            "{\"email\":\"john.doe@example.com\"}",
            "{\"name\":\"John\",\"email\":\"john.doe\"}"
    })
    @DisplayName("Create Borrower with Invalid Request")
    void createBorrower_InvalidRequest_NullName_ThrowsValidationException(String requestBody) throws Exception {

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/borrower/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isBadRequest());
    }
}

