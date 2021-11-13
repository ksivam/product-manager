package com.galvanize.prodman.service;

import com.galvanize.prodman.model.Currency;
import com.galvanize.prodman.model.ProductDTO;

/**
 * @author Krishna Sadasivam
 */
public interface ProductService {
    /**
     * Creates a product
     *
     * @param productDTO The product.
     * @return the product id.
     */
    Integer create(final ProductDTO productDTO);

    /**
     * Gets a product and augments the price of the product to reflect the currency.
     *
     * @param id       the product id.
     * @param currency the currency to which the price of the product is converted.
     * @return the product.
     */
    ProductDTO get(final Integer id, final Currency currency);

    /**
     * Increments the product view count by one.
     *
     * @param id the product id.
     */
    void incrementViewCountByOne(final Integer id);

    /**
     * Deleted the product by id.
     *
     * @param id the product id.
     */
    void delete(final Integer id);
}
