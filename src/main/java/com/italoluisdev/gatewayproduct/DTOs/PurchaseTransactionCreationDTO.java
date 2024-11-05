package com.italoluisdev.gatewayproduct.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseTransactionCreationDTO {

    public PurchaseTransactionCreationDTO(String description, Double purchaseAmount) {
        this.description = description;
        this.purchaseAmount = purchaseAmount;
    }

    private String description;
    private Double purchaseAmount;

}
