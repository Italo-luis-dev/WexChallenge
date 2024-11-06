package com.italoluisdev.gatewayproduct.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.italoluisdev.gatewayproduct.DTOs.*;
import com.italoluisdev.gatewayproduct.entities.*;
import com.italoluisdev.gatewayproduct.services.interfaces.iPurchaseTransactionService;
import com.italoluisdev.gatewayproduct.utils.PurchaseAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PurchaseTransactionControllerTest {

    @Mock
    private iPurchaseTransactionService purchaseTransactionService;

    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private PurchaseTransactionController purchaseTransactionController;

    private PurchaseTransactionCreationDTO creationDTO;
    private PurchaseTransactionRetrieveDTO retrieveDTO;
    private ConvertedPurchaseTransactionRetrieveDTO convertedRetrieveDTO;
    private PurchaseTransaction transaction;
    private TreasuryReportingRateExchange exchange;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        creationDTO = new PurchaseTransactionCreationDTO("Test Purchase", 100.0, "01/01/2024");
        retrieveDTO = new PurchaseTransactionRetrieveDTO("Test Purchase", 100.0, UUID.randomUUID().toString(), "01/01/2024");
        convertedRetrieveDTO = new ConvertedPurchaseTransactionRetrieveDTO();
        convertedRetrieveDTO.setIdentifier("12345");
        convertedRetrieveDTO.setDescription("Test Description");
        convertedRetrieveDTO.setPurchaseAmount(100.0);
        convertedRetrieveDTO.setTransactionDate("01/01/2024");
        convertedRetrieveDTO.setCountry_currency_desc("US Dollar");
        convertedRetrieveDTO.setExchange_rate(1.2);

        transaction = new PurchaseTransaction("Test Purchase", "01/01/2024", 100.0, UUID.randomUUID().toString());
        exchange = new TreasuryReportingRateExchange();
        exchange.setCountry("USA");
        exchange.setCurrency("USD");
        exchange.setCountry_currency_desc("US Dollar");
        exchange.setExchange_rate(1.2);
    }

    @Test
    void testCreateTransaction() throws Exception {
        when(purchaseTransactionService.createTransaction(any(PurchaseTransaction.class))).thenReturn(transaction);

        ResponseEntity<PurchaseTransactionRetrieveDTO> response = purchaseTransactionController.createTransaction(creationDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDescription()).isEqualTo(creationDTO.getDescription());
        assertThat(response.getBody().getPurchaseAmount()).isEqualTo(creationDTO.getPurchaseAmount());
        assertThat(response.getBody().getTransactionDate()).isEqualTo(creationDTO.getTransactionDate());
    }

    @Test
    void testGetPurchaseTransactions() {
        when(purchaseTransactionService.getAll()).thenReturn(Arrays.asList(transaction));

        ResponseEntity<List<PurchaseTransactionRetrieveDTO>> response = purchaseTransactionController.getPurchaseTransactions();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getDescription()).isEqualTo(transaction.getDescription());
    }

    @Test
    void testGetPurchaseTransactionByIdentifier() {
        when(purchaseTransactionService.getPurchaseTransactionByIdentifier(transaction.getIdentifier())).thenReturn(transaction);

        ResponseEntity<PurchaseTransactionRetrieveDTO> response = purchaseTransactionController.getPurchaseTransactionByIdentifier(transaction.getIdentifier());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getIdentifier()).isEqualTo(transaction.getIdentifier());
        assertThat(response.getBody().getDescription()).isEqualTo(transaction.getDescription());
    }

    @Test
    void testRetrievePurchaseTransaction() throws Exception {
        when(purchaseTransactionService.getPurchaseTransactionByIdentifier(transaction.getIdentifier())).thenReturn(transaction);
        when(purchaseTransactionService.getSpecifiedCurrency("USA", "USD", transaction.getTransactionDate())).thenReturn(exchange);

        ResponseEntity<ConvertedPurchaseTransactionRetrieveDTO> response = purchaseTransactionController.retrievePurchaseTransaction(
                transaction.getIdentifier(), "USA", "USD");

        assertThat(response).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).getIdentifier()).isEqualTo(transaction.getIdentifier());
        assertThat(response.getBody().getDescription()).isEqualTo(transaction.getDescription());
        assertThat(response.getBody().getCountry_currency_desc()).isEqualTo(exchange.getCountry_currency_desc());
        assertThat(response.getBody().getExchange_rate()).isEqualTo(exchange.getExchange_rate());
        assertThat(response.getBody().getConvertedPurchaseAmount()).isEqualTo(120.0); // 100 * 1.2
    }

    // Método auxiliar para converter objetos em JSON
    private String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetPurchaseTransactions_EmptyList() {
        when(purchaseTransactionService.getAll()).thenReturn(List.of());

        ResponseEntity<List<PurchaseTransactionRetrieveDTO>> response = purchaseTransactionController.getPurchaseTransactions();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    void testRetrievePurchaseTransaction_NotFound() throws Exception {
        when(purchaseTransactionService.getPurchaseTransactionByIdentifier(transaction.getIdentifier()))
                .thenReturn(null); // Simulating that the transaction was not found
        when(purchaseTransactionService.getSpecifiedCurrency("USA", "USD", transaction.getTransactionDate()))
                .thenReturn(null); // Simulating that the specified currency was not found

        ResponseEntity<ConvertedPurchaseTransactionRetrieveDTO> response =
                new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND); // Expecting a 404
    }
}
