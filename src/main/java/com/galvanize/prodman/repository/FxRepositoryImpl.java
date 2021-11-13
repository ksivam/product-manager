package com.galvanize.prodman.repository;

import com.galvanize.prodman.model.Currency;
import com.galvanize.prodman.model.FxResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

/**
 * @author Krishna Sadasivam
 */
@Service
public class FxRepositoryImpl implements FxRepository {
    private static final String SUPPORTED_CURRENCIES = String.format("%s,%s,%s,%s",
            Currency.USD, Currency.CAD, Currency.EUR, Currency.GBP);
    private final String fxApiUrl;
    private final String fxApiKey;
    private final RestTemplate restTemplate;

    public FxRepositoryImpl(final RestTemplateBuilder restTemplateBuilder,
                            @Value("${fx.api.url}") String fxApiUrl,
                            @Value("${fx.api.key}") String fxApiKey) {
        this.fxApiUrl = fxApiUrl;
        this.fxApiKey = fxApiKey;
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Map<String, Double> getQuotes() {
        String endPoint = String.format("%s?access_key=%s&currencies=%s&format=1", fxApiUrl, fxApiKey, SUPPORTED_CURRENCIES);
        FxResponse fxResponse = restTemplate.getForObject(endPoint, FxResponse.class);
        return Objects.requireNonNull(fxResponse).getQuotes();
    }
}
