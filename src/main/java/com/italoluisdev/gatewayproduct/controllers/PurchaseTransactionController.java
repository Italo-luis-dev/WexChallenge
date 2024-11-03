package com.italoluisdev.gatewayproduct.controllers;

import com.italoluisdev.gatewayproduct.DTOs.PurchaseTransactionCreationDTO;
import com.italoluisdev.gatewayproduct.DTOs.PurchaseTransactionRetrieveDTO;
import com.italoluisdev.gatewayproduct.entities.PurchaseTransaction;
import com.italoluisdev.gatewayproduct.mappers.PurchaseTransactionMapper;
import com.italoluisdev.gatewayproduct.services.interfaces.iPurchaseTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("purachaseTransactions")
public class PurchaseTransactionController {
    @Autowired
    private iPurchaseTransactionService purchaseTransactionService;
    private final PurchaseTransactionMapper transactionMapper = PurchaseTransactionMapper.INSTANCE;

    @ResponseBody
    @PostMapping(value = "/create")
    public ResponseEntity<PurchaseTransactionRetrieveDTO> createTransaction (@RequestBody PurchaseTransactionCreationDTO purchaseTransactionRetrieveDTO){

        PurchaseTransaction purchaseTransaction =  transactionMapper.toModel(purchaseTransactionRetrieveDTO);
        return new ResponseEntity<PurchaseTransactionRetrieveDTO>(transactionMapper.toDto(
                purchaseTransactionService.createTransaction(purchaseTransaction)
        ), HttpStatus.CREATED
        );
    }

    @GetMapping
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

}
