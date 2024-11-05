package com.italoluisdev.gatewayproduct.services.interfaces;

import com.italoluisdev.gatewayproduct.entities.Currency;
import com.italoluisdev.gatewayproduct.entities.PurchaseTransaction;

import java.io.IOException;
import java.util.List;

public interface iPurchaseTransactionService {
    PurchaseTransaction createTransaction(PurchaseTransaction purchaseTransaction);

    List<PurchaseTransaction> getAll();

    PurchaseTransaction getPurchaseTransactionByIdentifier(String identifier);

    Currency getExchangeRate(String country, String currency) throws IOException;
}
