package com.galvanize.prodman.service;

import com.galvanize.prodman.domain.Quote;
import com.galvanize.prodman.model.Currency;

import java.util.Optional;

/**
 * @author Krishna Sadasivam
 */
public interface FxService {

  /**
   * Gets the forex quote for the currency.
   *
   * @param currency the currency for which a forex quote is returned.
   * @return the forex quote.
   */
  Optional<Quote> get(Currency currency);
}
