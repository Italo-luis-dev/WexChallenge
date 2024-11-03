package com.italoluisdev.gatewayproduct.services.interfaces;

import com.italoluisdev.gatewayproduct.entities.PurchaseTransaction;

import java.util.List;

public interface iPurchaseTransactionService {
    PurchaseTransaction createTransaction(PurchaseTransaction purchaseTransaction);

    List<PurchaseTransaction> getAll();
}
