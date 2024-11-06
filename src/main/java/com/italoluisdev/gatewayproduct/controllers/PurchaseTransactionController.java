package com.italoluisdev.gatewayproduct.controllers;

import com.italoluisdev.gatewayproduct.DTOs.ConvertedPurchaseTransactionRetrieveDTO;
import com.italoluisdev.gatewayproduct.DTOs.PurchaseTransactionCreationDTO;
import com.italoluisdev.gatewayproduct.DTOs.PurchaseTransactionRetrieveDTO;
import com.italoluisdev.gatewayproduct.entities.TreasuryReportingRateExchange;
import com.italoluisdev.gatewayproduct.entities.PurchaseTransaction;
import com.italoluisdev.gatewayproduct.mappers.PurchaseTransactionMapper;
import com.italoluisdev.gatewayproduct.services.interfaces.iPurchaseTransactionService;
import com.italoluisdev.gatewayproduct.utils.ConversionNotAllowedException;
import com.italoluisdev.gatewayproduct.utils.PurchaseAmountException;
import com.italoluisdev.gatewayproduct.utils.PurchaseTransactionControllerDoc;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/purchaseTransactions")
public class PurchaseTransactionController implements PurchaseTransactionControllerDoc {
    @Autowired
    private iPurchaseTransactionService purchaseTransactionService;
    private final PurchaseTransactionMapper transactionMapper = PurchaseTransactionMapper.INSTANCE;

    @ResponseBody
    @PostMapping(value = "/create")
    public ResponseEntity<PurchaseTransactionRetrieveDTO> createTransaction
            (@RequestBody PurchaseTransactionCreationDTO purchaseTransactionRetrieveDTO) {

        PurchaseTransaction purchaseTransaction =  transactionMapper.toModel(purchaseTransactionRetrieveDTO);

        return new ResponseEntity<>(transactionMapper.toPurchaseTransactionRetrieveDto(
                purchaseTransactionService.createTransaction(purchaseTransaction)
        ), HttpStatus.CREATED
        );
    }

    @GetMapping(value = "/getPurchaseTransactions")
    @ResponseBody
    public ResponseEntity<List<PurchaseTransactionRetrieveDTO>> getPurchaseTransactions (){
        return new ResponseEntity<>(
                purchaseTransactionService.getAll()
                        .stream()
                        .map(transactionMapper::toPurchaseTransactionRetrieveDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/getPurchaseTransactionByIdentifier")
    @ResponseBody
    public ResponseEntity<PurchaseTransactionRetrieveDTO> getPurchaseTransactionByIdentifier (@RequestParam String identifier){
        PurchaseTransaction purchaseTransactionByIdentifier = purchaseTransactionService.getPurchaseTransactionByIdentifier(identifier);
        if(purchaseTransactionByIdentifier == null)
            return new ResponseEntity<>(null,
                    HttpStatus.NOT_FOUND
            );

        return new ResponseEntity<>(
                transactionMapper.toPurchaseTransactionRetrieveDto(purchaseTransactionByIdentifier),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/retrieveConvertedPurchaseTransaction")
    @ResponseBody
    public ResponseEntity<ConvertedPurchaseTransactionRetrieveDTO> retrievePurchaseTransaction
            (@RequestParam String identifier, @RequestParam String country, @RequestParam String currency)
            throws IOException, ConversionNotAllowedException
    {

        PurchaseTransaction purchaseTransactionByIdentifier = purchaseTransactionService
                .getPurchaseTransactionByIdentifier(identifier);

        if(purchaseTransactionByIdentifier == null)
            return new ResponseEntity<>(null,
                    HttpStatus.NOT_FOUND
            );

        TreasuryReportingRateExchange specifiedCurrency = purchaseTransactionService
                .getSpecifiedCurrency(country, currency, purchaseTransactionByIdentifier.getTransactionDate());

        if(specifiedCurrency == null)
            return new ResponseEntity<>(null,
                    HttpStatus.NOT_FOUND
            );

        return new ResponseEntity<>(transactionMapper.toConvertedPurchaseTransactionRetrieveDTO(
                purchaseTransactionByIdentifier,
                specifiedCurrency),
                HttpStatus.OK);
    }

}
