package com.italoluisdev.gatewayproduct.mappers;

import com.italoluisdev.gatewayproduct.DTOs.ConvertedPurchaseTransactionRetrieveDTO;
import com.italoluisdev.gatewayproduct.DTOs.PurchaseTransactionCreationDTO;
import com.italoluisdev.gatewayproduct.DTOs.PurchaseTransactionRetrieveDTO;
import com.italoluisdev.gatewayproduct.entities.TreasuryReportingRateExchange;
import com.italoluisdev.gatewayproduct.entities.PurchaseTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PurchaseTransactionMapper {

    PurchaseTransactionMapper INSTANCE = Mappers.getMapper(PurchaseTransactionMapper.class);

    PurchaseTransaction toModel(PurchaseTransactionCreationDTO dto);

    PurchaseTransactionRetrieveDTO toPurchaseTransactionRetrieveDto(PurchaseTransaction purchaseTransaction);


    ConvertedPurchaseTransactionRetrieveDTO toConvertedPurchaseTransactionRetrieveDTO
            (PurchaseTransaction purchaseTransaction, TreasuryReportingRateExchange treasuryReportingRateExchange);

}
