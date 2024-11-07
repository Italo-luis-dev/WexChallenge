package com.italoluisdev.gatewayproduct.services.interfaces;

import com.italoluisdev.gatewayproduct.DTOs.ConvertedPurchaseTransactionRetrieveDTO;
import com.italoluisdev.gatewayproduct.entities.TreasuryReportingRateExchange;
import com.italoluisdev.gatewayproduct.entities.PurchaseTransaction;
import com.italoluisdev.gatewayproduct.utils.ConversionNotAllowedException;
import com.italoluisdev.gatewayproduct.utils.PurchaseAmountException;

import java.io.IOException;
import java.time.DateTimeException;
import java.util.List;

public interface iPurchaseTransactionService {
    PurchaseTransaction createTransaction(PurchaseTransaction purchaseTransaction) throws DateTimeException;

    List<PurchaseTransaction> getAll();

    PurchaseTransaction getPurchaseTransactionByIdentifier(String identifier);

    TreasuryReportingRateExchange getSpecifiedCurrency(String country, String currency, String transactionDate) throws IOException, ConversionNotAllowedException;

    ConvertedPurchaseTransactionRetrieveDTO formatPurchaseTransaction(PurchaseTransaction purchaseTransactionByIdentifier, TreasuryReportingRateExchange specifiedTreasuryReportingRateExchange);
}
