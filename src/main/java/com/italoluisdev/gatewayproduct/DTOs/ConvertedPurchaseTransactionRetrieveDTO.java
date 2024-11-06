package com.italoluisdev.gatewayproduct.DTOs;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.italoluisdev.gatewayproduct.entities.TreasuryReportingRateExchange;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.math3.util.Precision;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ConvertedPurchaseTransactionRetrieveDTO {

    @JsonProperty("Identifier")
    private String identifier;

    @JsonProperty("Description")
    private String description;
    @JsonProperty("Original US Dollar Purchase Amount")
    private double purchaseAmount;

    @JsonProperty("Transaction Date")
    private String transactionDate;

    @JsonProperty("Country and target currency")
    private String country_currency_desc;

    @JsonProperty("Exchange Rate")
    private double exchange_rate;

    @Getter(AccessLevel.NONE)
    @JsonProperty("Converted Purchase Amount")
    private double convertedPurchaseAmount;

    public double getConvertedPurchaseAmount() {
        return Precision.round(this.purchaseAmount * this.exchange_rate, 2);
    }


}
