package com.galvanize.prodman.rest;

import com.galvanize.prodman.model.Currency;
import com.galvanize.prodman.model.ProductDTO;
import com.galvanize.prodman.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.galvanize.prodman.util.EnumMapper.getEnumFromString;


@RestController
@RequestMapping(value = "/api/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "product/{productId}/{currency}")
    public ProductDTO get(@PathVariable String productId, @PathVariable String currency) {
        Currency c = getEnumFromString(Currency.class, currency).orElse(Currency.USD);
        Integer id = Integer.valueOf(productId);

        ProductDTO productDTO = this.productService.get(id, c);
        this.productService.incrementViewCountByOne(id);
        return productDTO;
    }

    @GetMapping(value = "product/{productId}")
    public ProductDTO getByDefaultCurrency(@PathVariable String productId) {
        return get(productId, Currency.USD.name());
    }
}
