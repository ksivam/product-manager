package com.galvanize.prodman.repository;

import com.galvanize.prodman.domain.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Krishna Sadasivam
 */
public interface QuoteRepository extends JpaRepository<Quote, String> {
}
