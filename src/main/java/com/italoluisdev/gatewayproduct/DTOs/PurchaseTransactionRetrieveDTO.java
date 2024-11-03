package com.italoluisdev.gatewayproduct.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PurchaseTransactionRetrieveDTO {

    private String description;
    private Double purchaseAmount;
    private UUID identifier;
    private String transactionDate;

}
