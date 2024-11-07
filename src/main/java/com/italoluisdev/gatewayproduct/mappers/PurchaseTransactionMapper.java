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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "identifier", ignore = true)
    PurchaseTransaction toModel(PurchaseTransactionCreationDTO dto);

    PurchaseTransactionRetrieveDTO toPurchaseTransactionRetrieveDto(PurchaseTransaction purchaseTransaction);


    @Mapping(target = "convertedPurchaseAmount", ignore = true)
    ConvertedPurchaseTransactionRetrieveDTO toConvertedPurchaseTransactionRetrieveDTO
            (PurchaseTransaction purchaseTransaction, TreasuryReportingRateExchange treasuryReportingRateExchange);

}
