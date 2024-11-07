package com.italoluisdev.gatewayproduct.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "PurchaseTransaction")
@Getter
@Setter
@NoArgsConstructor
public class PurchaseTransaction {

    public PurchaseTransaction(String description, String transactionDate, double purchaseAmount, String uuid) {
        this.description = description;
        this.transactionDate = transactionDate;
        this.purchaseAmount = purchaseAmount;
        this.identifier = uuid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "description", length = 50)
    private String description;

    @Column(name = "transactionDate")
    private String transactionDate;

    @Column(name = "purchaseAmount")
    private double purchaseAmount;

    @Column(name = "identifier")
    private String identifier;
}
