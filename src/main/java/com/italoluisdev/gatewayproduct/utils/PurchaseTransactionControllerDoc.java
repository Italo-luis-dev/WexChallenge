package com.italoluisdev.gatewayproduct.utils;


import com.italoluisdev.gatewayproduct.DTOs.ConvertedPurchaseTransactionRetrieveDTO;
import com.italoluisdev.gatewayproduct.DTOs.PurchaseTransactionCreationDTO;
import com.italoluisdev.gatewayproduct.DTOs.PurchaseTransactionRetrieveDTO;
import com.italoluisdev.gatewayproduct.utils.exceptions.ConversionNotAllowedException;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@OpenAPIDefinition(info = @Info(title = "WEX GATEWAY PRODUCT"))
public interface PurchaseTransactionControllerDoc {

    @Operation(summary = "Create and store a purchase transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Purchase transaction created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PurchaseTransactionCreationDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input provided",
                    content = @Content) })
    ResponseEntity<PurchaseTransactionRetrieveDTO> createTransaction
            (@io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Example of a purchase transaction", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PurchaseTransactionCreationDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "Description": "purchase transaction's description",
                                      "US Dollar Purchase Amount": 15.54,
                                      "Transaction Date": "11/01/2024 (MM/dd/yyyy)"
                                    }""")))
             @RequestBody PurchaseTransactionCreationDTO purchaseTransactionRetrieveDTO);


    @Operation(summary = "Retrieves all purchase transactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Purchase transactions retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PurchaseTransactionRetrieveDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input provided",
                    content = @Content) })
    ResponseEntity<List<PurchaseTransactionRetrieveDTO>> getPurchaseTransactions ();


    @Operation(summary = "Retrieves a purchase transaction by its identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Purchase transaction retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PurchaseTransactionCreationDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Unable to recover purchase transaction",
            content = @Content)})

    ResponseEntity<PurchaseTransactionRetrieveDTO> getPurchaseTransactionByIdentifier (
            @Parameter(description = "Purchase transaction identifier") @RequestParam String identifier);


    @Operation(summary = "Retrieves a purchase transaction by its identifier, converting the purchase amount" +
            "accordingly by the country and currency provided",
            description = "This endpoint is responsible for retrieving a purchase transaction with a converted purchase amount. " +
                    "To retrieve a purchase transaction, inform its **IDENTIFIER**, " +
                    "followed by the country of choice (ex: United Kingom) and its currency (ex: Pound). " +
                    "The supported currencies follows the Treasury Reporting Rates of Exchange API. " +
                    "<br><br> If you have any questions about the country or currency, copy the link below and paste it into your" +
                    " browser to access the Treasury Reporting Rates of Exchange API. <br>" +
                    "https://fiscaldata.treasury.gov/datasets/treasury-reporting-rates-exchange/treasury-reporting-rates-of-exchange")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Purchase transaction retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConvertedPurchaseTransactionRetrieveDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Unable to recover purchase transaction",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Conversion not allowed",
                    content = @Content) })
    ResponseEntity<ConvertedPurchaseTransactionRetrieveDTO> retrievePurchaseTransaction
            (@Parameter(description = "Purchase transaction identifier") @RequestParam String identifier,
             @Parameter(description = "Country") @RequestParam String country,
             @Parameter(description = "Currency") @RequestParam String currency) throws IOException, ConversionNotAllowedException;

}
