package com.italoluisdev.gatewayproduct.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PurchaseTransactionCreationDTO {

    private String description;
    private Double purchaseAmount;

}
