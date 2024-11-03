package com.italoluisdev.gatewayproduct.services.implementations;


import com.italoluisdev.gatewayproduct.entities.PurchaseTransaction;
import com.italoluisdev.gatewayproduct.repositories.PurchaseTransactionRepository;
import com.italoluisdev.gatewayproduct.services.interfaces.iPurchaseTransactionService;
import lombok.AllArgsConstructor;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PurchaseTransactionService implements iPurchaseTransactionService {

    private PurchaseTransactionRepository purchaseTransactionRepository;

    @Override
    public PurchaseTransaction createTransaction(PurchaseTransaction purchaseTransaction) {

        formatPurchaseTransaction(purchaseTransaction);

        return purchaseTransactionRepository.save(purchaseTransaction);
    }

    private static void formatPurchaseTransaction(PurchaseTransaction purchaseTransaction) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        purchaseTransaction.setTransactionDate(sdf.format(new Date()));
        purchaseTransaction.setIdentifier(UUID.randomUUID());
        purchaseTransaction.setPurchaseAmount(Precision.round(purchaseTransaction.getPurchaseAmount(), 2));
    }

    @Override
    public List<PurchaseTransaction> getAll() {
        return purchaseTransactionRepository.findAll();
    }
}
