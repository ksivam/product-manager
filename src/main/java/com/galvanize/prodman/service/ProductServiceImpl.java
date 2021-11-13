package com.galvanize.prodman.service;

import com.galvanize.prodman.domain.Product;
import com.galvanize.prodman.domain.Quote;
import com.galvanize.prodman.model.Currency;
import com.galvanize.prodman.model.ProductDTO;
import com.galvanize.prodman.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final FxService fxService;

    public ProductServiceImpl(final ProductRepository productRepository, final FxService fxService) {
        this.productRepository = productRepository;
        this.fxService = fxService;
    }

    @Override
    public Integer create(final ProductDTO productDTO) {
        final Product product = new Product();
        mapToEntity(productDTO, product);
        return productRepository.saveAndFlush(product).getId();
    }

    @Override
    public ProductDTO get(final Integer id, final Currency currency) {
        Optional<Product> product = productRepository.findById(id);
        Optional<Quote> quote = this.fxService.get(currency);
        return mapToDto(product.get(), quote.get());
    }

    @Override
    public void incrementViewCountByOne(Integer id) {
        this.productRepository.incrementViewsByCount(id, 1);
    }

    @Override
    public void delete(final Integer id) {
        productRepository.deleteById(id);
    }

    private Product mapToEntity(final ProductDTO productDTO, final Product product) {
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setViews(0);
        product.setDeleted(false);
        return product;
    }

    private ProductDTO mapToDto(final Product product, final Quote quote) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice() * quote.getValue());
        productDTO.setViews(product.getViews());
        return productDTO;
    }
}
