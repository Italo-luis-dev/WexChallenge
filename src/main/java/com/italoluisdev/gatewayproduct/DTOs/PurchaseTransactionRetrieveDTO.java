package com.italoluisdev.gatewayproduct.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseTransactionRetrieveDTO {

    public PurchaseTransactionRetrieveDTO(String description, Double purchaseAmount, String identifier, String transactionDate) {
        this.description = description;
        this.purchaseAmount = purchaseAmount;
        this.identifier = identifier;
        this.transactionDate = transactionDate;
    }

    private String description;
    private Double purchaseAmount;
    private String identifier;
    private String transactionDate;

}
