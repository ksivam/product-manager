package com.galvanize.prodman.repository;

import java.util.Map;

/**
 * @author Krishna Sadasivam
 */
public interface FxRepository {

    /**
     * Get currency quotes from forex repository.
     *
     * @return map of currency to its value.
     */
    Map<String, Double> getQuotes();
}
