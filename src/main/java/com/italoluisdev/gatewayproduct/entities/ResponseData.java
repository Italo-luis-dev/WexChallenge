package com.italoluisdev.gatewayproduct.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

@Getter
@Setter
public class ResponseData {
    private List<Currency> data;
}
