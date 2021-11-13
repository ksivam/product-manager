package com.galvanize.prodman.service;

import com.galvanize.prodman.domain.Quote;
import com.galvanize.prodman.model.Currency;
import com.galvanize.prodman.repository.FxRepository;
import com.galvanize.prodman.repository.QuoteRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FxServiceImpl implements FxService {

    private static final Currency DEFAULT_CURRENCY = Currency.USD;
    private final QuoteRepository quoteRepository;
    private final FxRepository fxRepository;

    public FxServiceImpl(final FxRepository fxRepository,
                         final QuoteRepository quoteRepository) {
        this.fxRepository = fxRepository;
        this.quoteRepository = quoteRepository;
        updateQuotes();
    }

    @Scheduled(fixedRate = 60 * 60 * 1000 /* one hour */)
    public void updateQuotes() {
        Set<Quote> quotes = this.fxRepository.getQuotes()
                .entrySet()
                .stream()
                .map(pair -> {
                    Quote q = new Quote();
                    q.setCurrency(pair.getKey());
                    q.setValue(pair.getValue());
                    return q;
                })
                .collect(Collectors.toSet());

        this.quoteRepository.saveAllAndFlush(quotes);
    }

    @Override
    public Optional<Quote> get(Currency currency) {
        String id = DEFAULT_CURRENCY.name() + currency.name();
        return this.quoteRepository.findById(id);
    }
}
