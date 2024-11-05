package com.italoluisdev.gatewayproduct.services.implementations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.italoluisdev.gatewayproduct.entities.Currency;
import com.italoluisdev.gatewayproduct.entities.PurchaseTransaction;
import com.italoluisdev.gatewayproduct.entities.ResponseData;
import com.italoluisdev.gatewayproduct.repositories.PurchaseTransactionRepository;
import com.italoluisdev.gatewayproduct.services.interfaces.iPurchaseTransactionService;
import lombok.AllArgsConstructor;
import org.apache.commons.math3.util.Precision;
import org.apache.commons.text.CaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PurchaseTransactionService implements iPurchaseTransactionService {

    private PurchaseTransactionRepository purchaseTransactionRepository;

    private final WebClient webClient = WebClient.builder()
            .build();

    @Override
    public PurchaseTransaction createTransaction(PurchaseTransaction purchaseTransaction) {

        formatPurchaseTransaction(purchaseTransaction);

        return purchaseTransactionRepository.save(purchaseTransaction);
    }

    private static void formatPurchaseTransaction(PurchaseTransaction purchaseTransaction) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        purchaseTransaction.setTransactionDate(sdf.format(new Date()));
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
    public Currency getExchangeRate(String country, String currency) throws IOException {
        //country_currency_desc:eq:Canada-Dollar,record_date:lte:2024-11-04
        String jsonArrayString = Files.readString(Paths.get("src/main/resources/allCountries_Currency_Desc.json"));
        TypeReference<List<Currency>> typeReference = new TypeReference<List<Currency>>() {};
        ObjectMapper objectMapper = new ObjectMapper();
        List<Currency> currencyList = objectMapper.readValue(jsonArrayString, typeReference);
        Currency first = currencyList.stream()
                .filter(obj -> obj.getCountry().toUpperCase().equals(country.toUpperCase()))
                .filter(o -> o.getCurrency().toUpperCase().equals(currency.toUpperCase()))
                .findFirst().orElse(null);

        assert first != null;
        URI uriPascalCase = UriComponentsBuilder
                .fromUriString("https://api.fiscaldata.treasury.gov/services/api/fiscal_service/v1/accounting/od/rates_of_exchange")
                .queryParam("fields", "effective_date,country,currency,country_currency_desc,exchange_rate")
                .queryParam("sort", "-effective_date")
                .queryParam("filter", "country:eq:"+toPascalCase(first.getCountry()) +
                        ",currency:eq:"+toPascalCase(first.getCurrency()) +
                         ",record_date:lte:2024-11-04")
                .build()
                .toUri();

        Mono<ResponseData> data = null;

        URI uriUpperCase = UriComponentsBuilder
                .fromUriString("https://api.fiscaldata.treasury.gov/services/api/fiscal_service/v1/accounting/od/rates_of_exchange")
                .queryParam("fields", "effective_date,country,currency,country_currency_desc,exchange_rate")
                .queryParam("sort", "-effective_date")
                .queryParam("filter", "country:eq:"+first.getCountry().toUpperCase() +
                        ",currency:eq:"+first.getCurrency().toUpperCase() +
                        ",record_date:lte:2024-11-04")
                .build()
                .toUri();

        data = webClient.get()
                .uri(uriPascalCase)
                .retrieve()
                .bodyToMono(ResponseData.class);


        ResponseData block = data.block();
        if(((block != null ? block.getData().size() : 0) == 0)){
           data = webClient.get()
                    .uri(uriUpperCase)
                    .retrieve()
                    .bodyToMono(ResponseData.class);
        }
        block = data.block();

        System.out.println(uriPascalCase);
        System.out.println(uriUpperCase);
        return Objects.requireNonNull(block).getData().get(0);

    }

    public static String toPascalCase(String word) {
        return CaseUtils.toCamelCase(word, true, ' ');
    }
}
