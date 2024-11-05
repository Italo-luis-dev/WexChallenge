package com.italoluisdev.gatewayproduct.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Currency {
    private String country;
    private Date effective_date;
    private String currency;
    private String country_currency_desc;
    private double exchange_rate;
}
