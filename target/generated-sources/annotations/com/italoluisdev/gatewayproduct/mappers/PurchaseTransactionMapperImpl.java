package com.italoluisdev.gatewayproduct.mappers;

import com.italoluisdev.gatewayproduct.DTOs.PurchaseTransactionCreationDTO;
import com.italoluisdev.gatewayproduct.DTOs.PurchaseTransactionRetrieveDTO;
import com.italoluisdev.gatewayproduct.entities.PurchaseTransaction;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-02T20:14:19-0300",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.5 (Amazon.com Inc.)"
)
public class PurchaseTransactionMapperImpl implements PurchaseTransactionMapper {

    @Override
    public PurchaseTransaction toModel(PurchaseTransactionCreationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PurchaseTransaction purchaseTransaction = new PurchaseTransaction();

        purchaseTransaction.setDescription( dto.getDescription() );
        if ( dto.getPurchaseAmount() != null ) {
            purchaseTransaction.setPurchaseAmount( dto.getPurchaseAmount() );
        }

        return purchaseTransaction;
    }

    @Override
    public PurchaseTransactionRetrieveDTO toDto(PurchaseTransaction purchaseTransaction) {
        if ( purchaseTransaction == null ) {
            return null;
        }

        PurchaseTransactionRetrieveDTO purchaseTransactionRetrieveDTO = new PurchaseTransactionRetrieveDTO();

        purchaseTransactionRetrieveDTO.setDescription( purchaseTransaction.getDescription() );
        purchaseTransactionRetrieveDTO.setPurchaseAmount( purchaseTransaction.getPurchaseAmount() );
        purchaseTransactionRetrieveDTO.setIdentifier( purchaseTransaction.getIdentifier() );
        purchaseTransactionRetrieveDTO.setTransactionDate( purchaseTransaction.getTransactionDate() );

        return purchaseTransactionRetrieveDTO;
    }
}
