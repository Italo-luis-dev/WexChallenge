package com.italoluisdev.gatewayproduct.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseData {
    private List<TreasuryReportingRateExchange> data;
}
