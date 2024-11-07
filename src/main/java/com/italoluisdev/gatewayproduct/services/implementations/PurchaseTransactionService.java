package com.italoluisdev.gatewayproduct.services.implementations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.italoluisdev.gatewayproduct.DTOs.ConvertedPurchaseTransactionRetrieveDTO;
import com.italoluisdev.gatewayproduct.entities.TreasuryReportingRateExchange;
import com.italoluisdev.gatewayproduct.entities.PurchaseTransaction;
import com.italoluisdev.gatewayproduct.entities.ResponseData;
import com.italoluisdev.gatewayproduct.repositories.PurchaseTransactionRepository;
import com.italoluisdev.gatewayproduct.services.interfaces.iPurchaseTransactionService;
import com.italoluisdev.gatewayproduct.utils.exceptions.ConversionNotAllowedException;
import lombok.AllArgsConstructor;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PurchaseTransactionService implements iPurchaseTransactionService {

    private static String allCountriesCurrencyDescJson;

    static {
        try {
            allCountriesCurrencyDescJson = Files.readString(Paths.get("src/main/resources/allCountries_Currency_Desc.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private PurchaseTransactionRepository purchaseTransactionRepository;

    private final WebClient webClient = WebClient.builder()
            .build();

    @Override
    public PurchaseTransaction createTransaction(PurchaseTransaction purchaseTransaction)  {

        formatPurchaseTransaction(purchaseTransaction);

        return purchaseTransactionRepository.save(purchaseTransaction);
    }

    private static void formatPurchaseTransaction(PurchaseTransaction purchaseTransaction)  {
        if(!isValidDate(purchaseTransaction.getTransactionDate()))
            throw new DateTimeException("Invalid date. Use MM/dd/yyyy or a date before the current one.");

        purchaseTransaction.setTransactionDate(purchaseTransaction.getTransactionDate());
        purchaseTransaction.setIdentifier(UUID.randomUUID().toString());
        purchaseTransaction.setPurchaseAmount(Precision.round(purchaseTransaction.getPurchaseAmount(), 2));
    }

    @Override
    public List<PurchaseTransaction> getAll() {
        return purchaseTransactionRepository.findAll();
    }

    @Override
    public PurchaseTransaction getPurchaseTransactionByIdentifier(String identifier) {
        return purchaseTransactionRepository.findByIdentifier(identifier);
    }

    @Override
    public TreasuryReportingRateExchange getSpecifiedCurrency(String country, String currency, String transactionDate)
            throws IOException, ConversionNotAllowedException {
        TreasuryReportingRateExchange chosenTreasuryReportingRateExchange = getTRREByCountryCurrency(country, currency);
        ResponseData block = null;
        TreasuryReportingRateExchange treasuryReportingRateExchange =
                getResponseData(chosenTreasuryReportingRateExchange).getData().get(0);
        if(isWithinSixMonths(treasuryReportingRateExchange.getEffective_date(), transactionDate)){
            return treasuryReportingRateExchange;
        }else{
            throw new ConversionNotAllowedException();
        }
    }

    public static boolean isWithinSixMonths(LocalDate exchangeEffectiveDate, String transactionDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        try {

            LocalDate date2 = LocalDate.parse(transactionDate, formatter);

            Period period = Period.between(exchangeEffectiveDate, date2);
            int monthsDifference = Math.abs(period.getMonths() + period.getYears() * 12);

            return monthsDifference <= 6;

        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use MM/dd/yyyy.");
            return false;
        }
    }

    @Override
    public ConvertedPurchaseTransactionRetrieveDTO formatPurchaseTransaction(PurchaseTransaction purchaseTransactionByIdentifier, TreasuryReportingRateExchange specifiedTreasuryReportingRateExchange) {
        ConvertedPurchaseTransactionRetrieveDTO convertedPurchaseTransactionRetrieveDTO = new ConvertedPurchaseTransactionRetrieveDTO();

        return null;
    }

    private ResponseData getResponseData(TreasuryReportingRateExchange chosenTreasuryReportingRateExchange) {
        Mono<ResponseData> data;
        assert chosenTreasuryReportingRateExchange != null;
        URI uriPascalCase = formatUri(
                capitalizeFirstLetter(chosenTreasuryReportingRateExchange.getCountry()),
                capitalizeFirstLetter(chosenTreasuryReportingRateExchange.getCurrency()));

        URI uriUpperCase = formatUri(chosenTreasuryReportingRateExchange.getCountry().toUpperCase(), chosenTreasuryReportingRateExchange.getCurrency().toUpperCase());

        data = webClient.get()
                .uri(uriPascalCase)
                .retrieve()
                .bodyToMono(ResponseData.class);


        if(((data.block() != null ? data.block().getData().size() : 0) == 0)){
            data = webClient.get()
                    .uri(uriUpperCase)
                    .retrieve()
                    .bodyToMono(ResponseData.class);
        }
        return data.block();
    }

    private static URI formatUri(String chosenCurrency, String chosenCurrency1) {
        return UriComponentsBuilder
                .fromUriString("https://api.fiscaldata.treasury.gov/services/api/fiscal_service/v1/accounting/od/rates_of_exchange")
                .queryParam("fields", "effective_date,country,currency,country_currency_desc,exchange_rate")
                .queryParam("sort", "-effective_date")
                .queryParam("filter", "country:eq:"+ chosenCurrency +
                        ",currency:eq:"+ chosenCurrency1 +
                        ",record_date:lte:2024-11-04")
                .build()
                .toUri();
    }

    private static TreasuryReportingRateExchange getTRREByCountryCurrency(String country, String currency) throws IOException {
        TypeReference<List<TreasuryReportingRateExchange>> typeReference = new TypeReference<List<TreasuryReportingRateExchange>>() {};
        ObjectMapper objectMapper = new ObjectMapper();
        List<TreasuryReportingRateExchange> treasuryReportingRateExchangeList = objectMapper.readValue(allCountriesCurrencyDescJson, typeReference);
        return treasuryReportingRateExchangeList.stream()
                .filter(obj -> obj.getCountry().toUpperCase().equals(country.toUpperCase()))
                .filter(o -> o.getCurrency().toUpperCase().equals(currency.toUpperCase()))
                .findFirst().orElseThrow();
    }

    public static String capitalizeFirstLetter(String input) {
        String[] words = input.toLowerCase().split("\\s+");
        StringBuilder capitalized = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                capitalized.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }
        return capitalized.toString().trim();
    }

    public static boolean isValidDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        try {
            LocalDate date = LocalDate.parse(dateStr, formatter);
            return !date.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
