package com.italoluisdev.gatewayproduct.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.italoluisdev.gatewayproduct.DTOs.PurchaseTransactionCreationDTO;
import com.italoluisdev.gatewayproduct.DTOs.PurchaseTransactionRetrieveDTO;
import com.italoluisdev.gatewayproduct.entities.PurchaseTransaction;
import com.italoluisdev.gatewayproduct.services.implementations.PurchaseTransactionService;
import com.italoluisdev.gatewayproduct.services.interfaces.iPurchaseTransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PurchaseTransactionController.class)
@AutoConfigureMockMvc
public class PurchaseTransactionControllerTest {

    @Autowired
    private PurchaseTransactionController purchaseTransactionController;

    @MockBean
    private iPurchaseTransactionService purchaseTransactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
    private PurchaseTransaction purchaseTransaction;
    private PurchaseTransactionCreationDTO creationDTO;
    private PurchaseTransactionRetrieveDTO retrieveDTO;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testCreateTransaction() throws Exception {

        String description = "description";
        String transactionDate = "purchaseDate";
        double purchaseAmount = 10.1;
        UUID identifier = UUID.randomUUID();
        purchaseTransaction = new PurchaseTransaction(description,transactionDate,purchaseAmount,identifier);
        creationDTO = new PurchaseTransactionCreationDTO(description, purchaseAmount);
        retrieveDTO = new PurchaseTransactionRetrieveDTO(description,purchaseAmount,identifier, transactionDate);

        when(purchaseTransactionService.createTransaction(purchaseTransaction)).thenReturn(purchaseTransaction);

        // Executa o método da controller
        mockMvc.perform(post("/purchaseTransactions/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creationDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value(retrieveDTO.getDescription()))
                .andExpect(jsonPath("$.purchaseAmount").value(retrieveDTO.getPurchaseAmount()))
               ;
    }

    @Test
    void testGetPurchaseTransactions() throws Exception {
        String description = "description";
        String transactionDate = "purchaseDate";
        double purchaseAmount = 10.1;
        UUID identifier = UUID.randomUUID();
        purchaseTransaction = new PurchaseTransaction(description,transactionDate,purchaseAmount,identifier);
        creationDTO = new PurchaseTransactionCreationDTO(description, purchaseAmount);
        retrieveDTO = new PurchaseTransactionRetrieveDTO(description,purchaseAmount,identifier, transactionDate);

        List<PurchaseTransaction> transactions = Stream.of(purchaseTransaction).collect(Collectors.toList());
        List<PurchaseTransactionRetrieveDTO> expectedDTOs = Stream.of(retrieveDTO).collect(Collectors.toList());

        when(purchaseTransactionService.getAll()).thenReturn(transactions);

        // Realiza a chamada à controller com MockMvc
        mockMvc.perform(get("/purchaseTransactions/getPurchaseTransactions") // substitua com o mapeamento real
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].description").value(retrieveDTO.getDescription()))
                .andExpect(jsonPath("$[0].purchaseAmount").value(retrieveDTO.getPurchaseAmount()));
    }
}
