package com.italoluisdev.gatewayproduct.controllers;

import com.italoluisdev.gatewayproduct.DTOs.PurchaseTransactionCreationDTO;
import com.italoluisdev.gatewayproduct.DTOs.PurchaseTransactionRetrieveDTO;
import com.italoluisdev.gatewayproduct.entities.Currency;
import com.italoluisdev.gatewayproduct.entities.PurchaseTransaction;
import com.italoluisdev.gatewayproduct.mappers.PurchaseTransactionMapper;
import com.italoluisdev.gatewayproduct.services.interfaces.iPurchaseTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/purchaseTransactions")
public class PurchaseTransactionController {
    @Autowired
    private iPurchaseTransactionService purchaseTransactionService;
    private final PurchaseTransactionMapper transactionMapper = PurchaseTransactionMapper.INSTANCE;

    @ResponseBody
    @PostMapping(value = "/create")
    public ResponseEntity<PurchaseTransactionRetrieveDTO> createTransaction (@RequestBody PurchaseTransactionCreationDTO purchaseTransactionRetrieveDTO){

        PurchaseTransaction purchaseTransaction =  transactionMapper.toModel(purchaseTransactionRetrieveDTO);
        return new ResponseEntity<>(transactionMapper.toDto(
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
                        .map(transactionMapper::toDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/getPurchaseTransactionByIdentifier")
    @ResponseBody
    public ResponseEntity<PurchaseTransactionRetrieveDTO> getPurchaseTransactionByIdentifier (@RequestParam String identifier){
        return new ResponseEntity<>(
                transactionMapper.toDto(purchaseTransactionService.getPurchaseTransactionByIdentifier(identifier)),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/getExchangeRate")
    @ResponseBody
    public Currency getExchangeRate (@RequestParam String country, @RequestParam String currency) throws IOException {
        return purchaseTransactionService.getExchangeRate(country,currency);
    }

}
