package com.italoluisdev.gatewayproduct.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("Description")
    private String description;
    @JsonProperty("US Dollar Purchase Amount")
    private Double purchaseAmount;
    @JsonProperty("Identifier")
    private String identifier;
    @JsonProperty("Transaction Date")
    private String transactionDate;

}
