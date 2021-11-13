package com.galvanize.prodman.rest;

import com.galvanize.prodman.model.ProductDTO;
import com.galvanize.prodman.repository.QuoteRepository;
import com.galvanize.prodman.service.ProductServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.UUID;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Krishna Sadasivam
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductEnd2EndTest {
    @Autowired
    private QuoteRepository quoteRepository;
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ProductServiceImpl productService;

    @AfterEach
    void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "product");
    }

    @Test
    public void getProductWithDefaultCurrency() {
        ProductDTO expected = createNewProduct();
        ProductDTO actual = this.restTemplate.getForObject("http://localhost:" + port + "/api/product/" + expected.getId(),
                ProductDTO.class);

        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
        assertThat(actual.getViews()).isEqualTo(0);
    }

    @Test
    public void getProductWithCADCurrency() {
        ProductDTO expected = createNewProduct();
        ProductDTO actual = this.restTemplate.getForObject("http://localhost:" + port + "/api/product/" + expected.getId() + "/CAD",
                ProductDTO.class);

        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice() * quoteRepository.findById("USDCAD").get().getValue());
        assertThat(actual.getViews()).isEqualTo(0);
    }

    @Test
    public void getProductWithEURCurrency() {
        ProductDTO expected = createNewProduct();
        ProductDTO actual = this.restTemplate.getForObject("http://localhost:" + port + "/api/product/" + expected.getId() + "/EUR",
                ProductDTO.class);

        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice() * quoteRepository.findById("USDEUR").get().getValue());
        assertThat(actual.getViews()).isEqualTo(0);
    }

    @Test
    public void getProductWithGBPCurrency() {
        ProductDTO expected = createNewProduct();
        ProductDTO actual = this.restTemplate.getForObject("http://localhost:" + port + "/api/product/" + expected.getId() + "/GBP",
                ProductDTO.class);

        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice() * quoteRepository.findById("USDGBP").get().getValue());
        assertThat(actual.getViews()).isEqualTo(0);
    }

    @Test
    public void concurrentGetProducts() {
        ProductDTO expected = createNewProduct();
        int numberOfViews = 10;

        IntStream.range(0, numberOfViews).parallel().forEach(i ->
                this.restTemplate.getForObject("http://localhost:" + port + "/api/product/" + expected.getId() + "/USD",
                        ProductDTO.class));

        ProductDTO actual = this.restTemplate.getForObject("http://localhost:" + port + "/api/product/" + expected.getId() + "/USD",
                ProductDTO.class);

        assertThat(actual.getViews()).isEqualTo(numberOfViews);
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
    }


    private ProductDTO createNewProduct() {
        ProductDTO p = new ProductDTO();
        p.setName(UUID.randomUUID().toString());
        p.setDescription(UUID.randomUUID().toString());
        p.setPrice(10.50);

        Integer productId = this.productService.create(p);
        p.setId(productId);
        return p;
    }
}