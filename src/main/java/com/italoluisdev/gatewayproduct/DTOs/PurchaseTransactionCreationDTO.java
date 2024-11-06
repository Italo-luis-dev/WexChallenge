package com.italoluisdev.gatewayproduct.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseTransactionCreationDTO {

    public PurchaseTransactionCreationDTO(String description, Double purchaseAmount, String transactionDate) {
        this.description = description;
        this.purchaseAmount = purchaseAmount;
        this.transactionDate = transactionDate;
    }

    @JsonProperty("Description")
    private String description;
    @JsonProperty("US Dollar Purchase Amount")
    private Double purchaseAmount;
    @JsonProperty("Transaction Date")
    private String transactionDate;

}
