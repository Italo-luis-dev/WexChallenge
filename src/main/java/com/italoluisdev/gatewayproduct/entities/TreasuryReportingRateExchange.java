package com.italoluisdev.gatewayproduct.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class TreasuryReportingRateExchange {
    private String country;
    private LocalDate effective_date;
    private String currency;
    private String country_currency_desc;
    private double exchange_rate;
}
